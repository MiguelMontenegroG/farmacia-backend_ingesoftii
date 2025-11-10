package com.farmacia.controller;

import com.farmacia.model.Venta;
import com.farmacia.service.VentaService;
import com.farmacia.dto.ReporteVentasDTO;
import com.farmacia.dto.MetricasVentasDTO;
import com.farmacia.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "API para gestión de ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    @Operation(summary = "Obtener todas las ventas")
    public ResponseEntity<List<Venta>> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.obtenerTodasLasVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable String id) {
        Venta venta = ventaService.obtenerVentaPorId(id);
        if (venta != null) {
            return ResponseEntity.ok(venta);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener ventas por usuario")
    public ResponseEntity<List<Venta>> obtenerVentasPorUsuario(@PathVariable String usuarioId) {
        List<Venta> ventas = ventaService.obtenerVentasPorUsuario(usuarioId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener ventas por estado")
    public ResponseEntity<List<Venta>> obtenerVentasPorEstado(@PathVariable String estado) {
        List<Venta> ventas = ventaService.obtenerVentasPorEstado(estado);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/vendedor/{vendedorId}")
    @Operation(summary = "Obtener ventas por vendedor")
    public ResponseEntity<List<Venta>> obtenerVentasPorVendedor(@PathVariable String vendedorId) {
        List<Venta> ventas = ventaService.obtenerVentasPorVendedor(vendedorId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/fecha")
    @Operation(summary = "Obtener ventas por rango de fechas")
    public ResponseEntity<List<Venta>> obtenerVentasPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Venta> ventas = ventaService.obtenerVentasPorFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/reporte")
    @Operation(summary = "Generar reporte de ventas")
    public ResponseEntity<ReporteVentasDTO> generarReporteVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        // Si no se especifican fechas, usar el último mes
        if (fechaInicio == null) {
            fechaInicio = LocalDateTime.now().minusMonths(1);
        }
        if (fechaFin == null) {
            fechaFin = LocalDateTime.now();
        }

        ReporteVentasDTO reporte = ventaService.generarReporteVentas(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/metricas")
    @Operation(summary = "Obtener métricas de ventas")
    public ResponseEntity<MetricasVentasDTO> obtenerMetricasVentas() {
        MetricasVentasDTO metricas = ventaService.obtenerMetricasVentas();
        return ResponseEntity.ok(metricas);
    }

    @PostMapping("/metricas/guardar")
    @Operation(summary = "Guardar métricas de ventas")
    public ResponseEntity<ApiResponse<String>> guardarMetricas(@RequestBody MetricasVentasDTO metricas) {
        ventaService.guardarMetrica(metricas);
        return ResponseEntity.ok(new ApiResponse<>(true, "Métricas guardadas exitosamente", null));
    }

    @PostMapping
    @Operation(summary = "Crear nueva venta")
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.guardarVenta(venta);
        return ResponseEntity.ok(nuevaVenta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar venta")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable String id, @RequestBody Venta venta) {
        venta.setId(id);
        Venta ventaActualizada = ventaService.guardarVenta(venta);
        return ResponseEntity.ok(ventaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta")
    public ResponseEntity<ApiResponse<String>> eliminarVenta(@PathVariable String id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta eliminada exitosamente", null));
    }
}