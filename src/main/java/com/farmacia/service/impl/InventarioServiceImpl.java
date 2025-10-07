package com.farmacia.service.impl;

import com.farmacia.dto.*;
import com.farmacia.model.Producto;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<InventarioDTO> obtenerTodoElInventario() {
        return productoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventarioDTO obtenerProductoPorId(String id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return convertToDTO(producto);
    }

    @Override
    public List<InventarioDTO> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InventarioDTO aumentarStock(String id, ActualizarStockRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setStock(producto.getStock() + request.getCantidad());
        Producto productoActualizado = productoRepository.save(producto);

        return convertToDTO(productoActualizado);
    }

    @Override
    @Transactional
    public InventarioDTO disminuirStock(String id, ActualizarStockRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        int nuevoStock = producto.getStock() - request.getCantidad();
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock no puede ser negativo. Stock actual: " + producto.getStock() +
                    ", intentando restar: " + request.getCantidad());
        }

        producto.setStock(nuevoStock);
        Producto productoActualizado = productoRepository.save(producto);

        return convertToDTO(productoActualizado);
    }

    private InventarioDTO convertToDTO(Producto producto) {
        return new InventarioDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                producto.getCategoria()
        );
    }

    @Override
    public List<AlertaStockDTO> obtenerAlertasStock() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .filter(p -> p.getStock() <= p.getStockMinimo())
                .map(this::convertToAlertaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertaStockDTO> obtenerProductosStockCritico() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .filter(p -> p.getStock() <= (p.getStockMinimo() * 0.5)) // 50% del mínimo
                .map(this::convertToAlertaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReporteInventarioDTO generarReporteInventario() {
        List<Producto> productos = productoRepository.findAll();

        // Calcular estadísticas básicas
        int totalProductos = productos.size();
        int productosStockBajo = (int) productos.stream()
                .filter(p -> p.getStock() <= p.getStockMinimo())
                .count();
        int productosSinStock = (int) productos.stream()
                .filter(p -> p.getStock() == 0)
                .count();

        // Calcular valor total del inventario
        BigDecimal valorTotalInventario = productos.stream()
                .map(p -> p.getPrecio().multiply(BigDecimal.valueOf(p.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Obtener top 10 productos con stock más bajo
        List<ProductoReporteDTO> topProductosBajoStock = productos.stream()
                .sorted(Comparator.comparingInt(Producto::getStock))
                .limit(10)
                .map(this::convertToProductoReporteDTO)
                .collect(Collectors.toList());

        // Calcular total por categoría
        Map<String, Long> totalPorCategoria = productos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin Categoría",
                        Collectors.counting()
                ));

        ReporteInventarioDTO reporte = new ReporteInventarioDTO();
        reporte.setTotalProductos(totalProductos);
        reporte.setProductosStockBajo(productosStockBajo);
        reporte.setProductosSinStock(productosSinStock);
        reporte.setValorTotalInventario(valorTotalInventario);
        reporte.setTopProductosBajoStock(topProductosBajoStock);
        reporte.setTotalPorCategoria(totalPorCategoria);

        return reporte;
    }

    @Override
    public void enviarAlertasAutomaticas() {
        // Este método podría ejecutarse automáticamente con @Scheduled
        // Por ahora solo registramos en logs las alertas
        List<AlertaStockDTO> alertas = obtenerAlertasStock();

        if (!alertas.isEmpty()) {
            System.out.println("=== ALERTAS DE STOCK BAJO ===");
            for (AlertaStockDTO alerta : alertas) {
                System.out.println("Producto: " + alerta.getNombreProducto() +
                        " - Stock: " + alerta.getStockActual() +
                        " - Mínimo: " + alerta.getStockMinimo() +
                        " - Nivel: " + alerta.getNivelAlerta());
            }
            // En un entorno real, aquí se enviaría emails o notificaciones
        }
    }

    private AlertaStockDTO convertToAlertaDTO(Producto producto) {
        AlertaStockDTO alerta = new AlertaStockDTO();
        alerta.setProductoId(producto.getId());
        alerta.setNombreProducto(producto.getNombre());
        alerta.setStockActual(producto.getStock());
        alerta.setStockMinimo(producto.getStockMinimo());

        // Determinar nivel de alerta
        if (producto.getStock() == 0) {
            alerta.setNivelAlerta("CRITICO");
            alerta.setMensaje("Producto agotado");
        } else if (producto.getStock() <= (producto.getStockMinimo() * 0.3)) {
            alerta.setNivelAlerta("CRITICO");
            alerta.setMensaje("Stock crítico");
        } else if (producto.getStock() <= producto.getStockMinimo()) {
            alerta.setNivelAlerta("BAJO");
            alerta.setMensaje("Stock bajo");
        }

        return alerta;
    }

    private ProductoReporteDTO convertToProductoReporteDTO(Producto producto) {
        return new ProductoReporteDTO(
                producto.getNombre(),
                producto.getStock(),
                producto.getStockMinimo(),
                producto.getPrecio(),
                producto.getCategoria() != null ? producto.getCategoria().getNombre() : "Sin Categoría"
        );
    }
}