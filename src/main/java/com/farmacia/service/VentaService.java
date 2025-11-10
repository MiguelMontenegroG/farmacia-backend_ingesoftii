package com.farmacia.service;

import com.farmacia.model.Venta;
import com.farmacia.dto.ReporteVentasDTO;
import com.farmacia.dto.MetricasVentasDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaService {

    // CRUD básico
    List<Venta> obtenerTodasLasVentas();
    Venta obtenerVentaPorId(String id);
    Venta guardarVenta(Venta venta);
    void eliminarVenta(String id);

    // Consultas específicas
    List<Venta> obtenerVentasPorUsuario(String usuarioId);
    List<Venta> obtenerVentasPorEstado(String estado);
    List<Venta> obtenerVentasPorVendedor(String vendedorId);
    List<Venta> obtenerVentasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Reportes
    ReporteVentasDTO generarReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Métricas
    MetricasVentasDTO obtenerMetricasVentas();
    void guardarMetrica(MetricasVentasDTO metrica);
}