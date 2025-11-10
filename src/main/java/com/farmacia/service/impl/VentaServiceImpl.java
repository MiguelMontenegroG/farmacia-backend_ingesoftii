package com.farmacia.service.impl;

import com.farmacia.model.Venta;
import com.farmacia.model.Usuario;
import com.farmacia.model.Producto;
import com.farmacia.repository.VentaRepository;
import com.farmacia.repository.MetricasVentasRepository;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.VentaService;
import com.farmacia.dto.ReporteVentasDTO;
import com.farmacia.dto.MetricasVentasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MetricasVentasRepository metricasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta obtenerVentaPorId(String id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminarVenta(String id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public List<Venta> obtenerVentasPorUsuario(String usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Venta> obtenerVentasPorEstado(String estado) {
        return ventaRepository.findByEstado(estado);
    }

    @Override
    public List<Venta> obtenerVentasPorVendedor(String vendedorId) {
        return ventaRepository.findByVendedorId(vendedorId);
    }

    @Override
    public List<Venta> obtenerVentasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }

    @Override
    public ReporteVentasDTO generarReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);

        // Calcular métricas básicas
        int totalVentas = ventas.size();
        BigDecimal totalIngresos = ventas.stream()
                .filter(v -> v.getTotal() != null)
                .map(v -> BigDecimal.valueOf(v.getTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal promedioVenta = totalVentas > 0 ?
                totalIngresos.divide(BigDecimal.valueOf(totalVentas), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        // Crear resumen de ventas
        List<ReporteVentasDTO.VentaResumenDTO> ventasResumen = ventas.stream()
                .map(this::convertirAVentaResumen)
                .collect(Collectors.toList());

        // Calcular productos más vendidos
        Map<String, Integer> productosVendidos = new HashMap<>();
        Map<String, BigDecimal> ingresosPorProducto = new HashMap<>();

        for (Venta venta : ventas) {
            if (venta.getItems() != null) {
                venta.getItems().forEach(item -> {
                    String nombreProducto = item.getNombreProducto() != null ? item.getNombreProducto() : "Producto desconocido";
                    productosVendidos.put(nombreProducto, productosVendidos.getOrDefault(nombreProducto, 0) + item.getCantidad());
                    BigDecimal precioTotal = item.getPrecioUnitario() != null ?
                            item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())) :
                            BigDecimal.ZERO;
                    ingresosPorProducto.put(nombreProducto, ingresosPorProducto.getOrDefault(nombreProducto, BigDecimal.ZERO).add(precioTotal));
                });
            }
        }

        List<ReporteVentasDTO.ProductoMasVendidoDTO> productosMasVendidos = productosVendidos.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new ReporteVentasDTO.ProductoMasVendidoDTO(
                        entry.getKey(),
                        entry.getValue(),
                        ingresosPorProducto.getOrDefault(entry.getKey(), BigDecimal.ZERO)
                ))
                .collect(Collectors.toList());

        // Ventas por día
        Map<LocalDate, List<Venta>> ventasPorFecha = ventas.stream()
                .filter(v -> v.getFechaVenta() != null)
                .collect(Collectors.groupingBy(v -> v.getFechaVenta().toLocalDate()));

        List<ReporteVentasDTO.VentasPorDiaDTO> ventasPorDia = ventasPorFecha.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    LocalDate fecha = entry.getKey();
                    List<Venta> ventasDia = entry.getValue();
                    int numeroVentas = ventasDia.size();
                    BigDecimal totalDia = ventasDia.stream()
                            .filter(v -> v.getTotal() != null)
                            .map(v -> BigDecimal.valueOf(v.getTotal()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new ReporteVentasDTO.VentasPorDiaDTO(fecha.atStartOfDay(), numeroVentas, totalDia);
                })
                .collect(Collectors.toList());

        return new ReporteVentasDTO(fechaInicio, fechaFin, totalVentas, totalIngresos, promedioVenta,
                                   ventasResumen, productosMasVendidos, ventasPorDia);
    }

    @Override
    public MetricasVentasDTO obtenerMetricasVentas() {
        List<Venta> todasLasVentas = ventaRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        // Calcular métricas generales
        int totalVentas = todasLasVentas.size();
        BigDecimal totalIngresos = todasLasVentas.stream()
                .filter(v -> v.getTotal() != null)
                .map(v -> BigDecimal.valueOf(v.getTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal promedioVenta = totalVentas > 0 ?
                totalIngresos.divide(BigDecimal.valueOf(totalVentas), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        // Contar por estado
        int ventasCompletadas = (int) todasLasVentas.stream().filter(v -> "COMPLETADA".equals(v.getEstado())).count();
        int ventasCanceladas = (int) todasLasVentas.stream().filter(v -> "CANCELADA".equals(v.getEstado())).count();
        int ventasReembolsadas = (int) todasLasVentas.stream().filter(v -> "REEMBOLSADA".equals(v.getEstado())).count();

        // Calcular ingresos del mes actual y anterior
        LocalDateTime inicioMesActual = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIN);
        LocalDateTime finMesActual = LocalDateTime.of(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()), LocalTime.MAX);

        LocalDateTime inicioMesAnterior = inicioMesActual.minusMonths(1);
        LocalDateTime finMesAnterior = finMesActual.minusMonths(1);

        BigDecimal ingresosMesActual = ventaRepository.findByFechaVentaBetween(inicioMesActual, finMesActual).stream()
                .filter(v -> v.getTotal() != null)
                .map(v -> BigDecimal.valueOf(v.getTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ingresosMesAnterior = ventaRepository.findByFechaVentaBetween(inicioMesAnterior, finMesAnterior).stream()
                .filter(v -> v.getTotal() != null)
                .map(v -> BigDecimal.valueOf(v.getTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double crecimientoIngresos = ingresosMesAnterior.compareTo(BigDecimal.ZERO) != 0 ?
                ((ingresosMesActual.subtract(ingresosMesAnterior)).divide(ingresosMesAnterior, 4, RoundingMode.HALF_UP)).doubleValue() * 100 :
                0.0;

        return new MetricasVentasDTO(ahora, totalVentas, totalIngresos, promedioVenta,
                                   ventasCompletadas, ventasCanceladas, ventasReembolsadas,
                                   ingresosMesActual, ingresosMesAnterior, crecimientoIngresos);
    }

    @Override
    public void guardarMetrica(MetricasVentasDTO metrica) {
        metricasRepository.save(metrica);
    }

    private ReporteVentasDTO.VentaResumenDTO convertirAVentaResumen(Venta venta) {
        String clienteNombre = "Desconocido";
        if (venta.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(venta.getUsuarioId()).orElse(null);
            if (usuario != null) {
                clienteNombre = usuario.getNombre() + " " + usuario.getApellido();
            }
        }

        return new ReporteVentasDTO.VentaResumenDTO(
                venta.getId(),
                venta.getNumeroVenta(),
                venta.getFechaVenta(),
                venta.getTotal() != null ? BigDecimal.valueOf(venta.getTotal()) : BigDecimal.ZERO,
                venta.getEstado(),
                clienteNombre
        );
    }
}