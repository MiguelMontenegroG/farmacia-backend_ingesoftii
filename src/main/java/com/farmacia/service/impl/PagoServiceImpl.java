package com.farmacia.service.impl;

import com.farmacia.dto.PagoRequest;
import com.farmacia.dto.PagoResponse;
import com.farmacia.dto.ItemVentaDTO;
import com.farmacia.dto.ActualizarStockRequest;
import com.farmacia.model.Pago;
import com.farmacia.model.ItemVenta;
import com.farmacia.model.Producto;
import com.farmacia.repository.PagoRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.PagoService;
import com.farmacia.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioService inventarioService;

    private final BigDecimal IMPUESTO = new BigDecimal("0.16"); // 16% de IVA

    @Override
    @Transactional
    public PagoResponse procesarPago(PagoRequest pagoRequest) {
        try {
            // 1. Validar productos y stock
            validarProductosYStock(pagoRequest.getItems());

            // 2. Validar recetas si es necesario
            validarRecetas(pagoRequest);

            // 3. Calcular totales
            CalculoTotales calculo = calcularTotales(pagoRequest.getItems());

            // 4. Crear entidad Pago
            Pago pago = crearPago(pagoRequest, calculo);

            // 5. Procesar según método de pago
            PagoResponse response = procesarMetodoPago(pago, pagoRequest);

            // 6. Si el pago es exitoso, actualizar inventario
            if ("COMPLETADO".equals(response.getEstado())) {
                actualizarInventario(pagoRequest.getItems());
                pago.setEstado("COMPLETADO");
                pago.setFechaPago(LocalDateTime.now());
            }

            // 7. Guardar pago
            Pago pagoGuardado = pagoRepository.save(pago);
            response.setId(pagoGuardado.getId());

            return response;

        } catch (Exception e) {
            return new PagoResponse("RECHAZADO", BigDecimal.ZERO, "Error en el pago: " + e.getMessage());
        }
    }

    private void validarProductosYStock(List<ItemVentaDTO> items) {
        for (ItemVentaDTO item : items) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));

            if (!producto.isActivo()) {
                throw new RuntimeException("Producto no disponible: " + producto.getNombre());
            }

            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock());
            }

            if (producto.isRequiereReceta() && item.isRequiereReceta()) {
                // Aquí se podría agregar una validación específica de receta
                // Por ahora solo se verifica que esté marcado como requiere receta
            }
        }
    }

    private void validarRecetas(PagoRequest pagoRequest) {
        boolean requiereReceta = pagoRequest.getItems().stream()
                .anyMatch(ItemVentaDTO::isRequiereReceta);

        if (requiereReceta && (pagoRequest.getRecetaMedica() == null || pagoRequest.getRecetaMedica().trim().isEmpty())) {
            throw new RuntimeException("Se requiere receta médica para algunos productos");
        }
    }

    private CalculoTotales calcularTotales(List<ItemVentaDTO> items) {
        BigDecimal subtotal = items.stream()
                .map(ItemVentaDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal impuestos = subtotal.multiply(IMPUESTO);
        BigDecimal total = subtotal.add(impuestos);

        return new CalculoTotales(subtotal, impuestos, total);
    }

    private Pago crearPago(PagoRequest pagoRequest, CalculoTotales calculo) {
        Pago pago = new Pago();

        // Convertir DTOs a entidades
        List<ItemVenta> items = pagoRequest.getItems().stream()
                .map(item -> new ItemVenta(
                        item.getProductoId(),
                        item.getNombreProducto(),
                        item.getCantidad(),
                        item.getPrecioUnitario(),
                        item.isRequiereReceta()
                ))
                .collect(Collectors.toList());

        pago.setItems(items);
        pago.setSubtotal(calculo.getSubtotal());
        pago.setImpuestos(calculo.getImpuestos());
        pago.setTotal(calculo.getTotal());
        pago.setMetodoPago(pagoRequest.getMetodoPago());
        pago.setClienteNombre(pagoRequest.getClienteNombre());
        pago.setClienteEmail(pagoRequest.getClienteEmail());
        pago.setSeguroMedico(pagoRequest.getSeguroMedico());
        pago.setRecetaMedica(pagoRequest.getRecetaMedica());

        return pago;
    }

    private PagoResponse procesarMetodoPago(Pago pago, PagoRequest pagoRequest) {
        // Simulación de procesamiento de pago
        // En una implementación real, aquí integrarías con pasarelas de pago

        String numeroTransaccion = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        PagoResponse response = new PagoResponse();
        response.setEstado("COMPLETADO");
        response.setTotal(pago.getTotal());
        response.setSubtotal(pago.getSubtotal());
        response.setImpuestos(pago.getImpuestos());
        response.setMetodoPago(pago.getMetodoPago());
        response.setNumeroTransaccion(numeroTransaccion);
        response.setMensaje("Pago procesado exitosamente");

        pago.setNumeroTransaccion(numeroTransaccion);

        return response;
    }

    private void actualizarInventario(List<ItemVentaDTO> items) {
        for (ItemVentaDTO item : items) {
            ActualizarStockRequest stockRequest = new ActualizarStockRequest(
                    item.getCantidad(),
                    "Venta - Pago procesado"
            );
            inventarioService.disminuirStock(item.getProductoId(), stockRequest);
        }
    }

    @Override
    public Pago obtenerPagoPorId(String id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    @Override
    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }

    @Override
    public List<Pago> obtenerPagosPorEstado(String estado) {
        return pagoRepository.findByEstado(estado);
    }

    @Override
    public PagoResponse confirmarPago(String pagoId) {
        Pago pago = obtenerPagoPorId(pagoId);
        pago.setEstado("COMPLETADO");
        pago.setFechaPago(LocalDateTime.now());
        pagoRepository.save(pago);

        return new PagoResponse("COMPLETADO", pago.getTotal(), "Pago confirmado exitosamente");
    }

    @Override
    public PagoResponse cancelarPago(String pagoId) {
        Pago pago = obtenerPagoPorId(pagoId);
        pago.setEstado("CANCELADO");
        pagoRepository.save(pago);

        return new PagoResponse("CANCELADO", pago.getTotal(), "Pago cancelado");
    }

    // Clase interna para cálculos
    private static class CalculoTotales {
        private final BigDecimal subtotal;
        private final BigDecimal impuestos;
        private final BigDecimal total;

        public CalculoTotales(BigDecimal subtotal, BigDecimal impuestos, BigDecimal total) {
            this.subtotal = subtotal;
            this.impuestos = impuestos;
            this.total = total;
        }

        public BigDecimal getSubtotal() { return subtotal; }
        public BigDecimal getImpuestos() { return impuestos; }
        public BigDecimal getTotal() { return total; }
    }
}
