package com.farmacia.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "metricas_ventas")
public class MetricasVentasDTO {
    @Id
    private String id;
    private LocalDateTime fechaCalculo;
    private int totalVentas;
    private BigDecimal totalIngresos;
    private BigDecimal promedioVenta;
    private int ventasCompletadas;
    private int ventasCanceladas;
    private int ventasReembolsadas;
    private BigDecimal ingresosMesActual;
    private BigDecimal ingresosMesAnterior;
    private double crecimientoIngresos;

    // Constructores
    public MetricasVentasDTO() {}

    public MetricasVentasDTO(LocalDateTime fechaCalculo, int totalVentas, BigDecimal totalIngresos,
                            BigDecimal promedioVenta, int ventasCompletadas, int ventasCanceladas,
                            int ventasReembolsadas, BigDecimal ingresosMesActual,
                            BigDecimal ingresosMesAnterior, double crecimientoIngresos) {
        this.fechaCalculo = fechaCalculo;
        this.totalVentas = totalVentas;
        this.totalIngresos = totalIngresos;
        this.promedioVenta = promedioVenta;
        this.ventasCompletadas = ventasCompletadas;
        this.ventasCanceladas = ventasCanceladas;
        this.ventasReembolsadas = ventasReembolsadas;
        this.ingresosMesActual = ingresosMesActual;
        this.ingresosMesAnterior = ingresosMesAnterior;
        this.crecimientoIngresos = crecimientoIngresos;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getFechaCalculo() { return fechaCalculo; }
    public void setFechaCalculo(LocalDateTime fechaCalculo) { this.fechaCalculo = fechaCalculo; }

    public int getTotalVentas() { return totalVentas; }
    public void setTotalVentas(int totalVentas) { this.totalVentas = totalVentas; }

    public BigDecimal getTotalIngresos() { return totalIngresos; }
    public void setTotalIngresos(BigDecimal totalIngresos) { this.totalIngresos = totalIngresos; }

    public BigDecimal getPromedioVenta() { return promedioVenta; }
    public void setPromedioVenta(BigDecimal promedioVenta) { this.promedioVenta = promedioVenta; }

    public int getVentasCompletadas() { return ventasCompletadas; }
    public void setVentasCompletadas(int ventasCompletadas) { this.ventasCompletadas = ventasCompletadas; }

    public int getVentasCanceladas() { return ventasCanceladas; }
    public void setVentasCanceladas(int ventasCanceladas) { this.ventasCanceladas = ventasCanceladas; }

    public int getVentasReembolsadas() { return ventasReembolsadas; }
    public void setVentasReembolsadas(int ventasReembolsadas) { this.ventasReembolsadas = ventasReembolsadas; }

    public BigDecimal getIngresosMesActual() { return ingresosMesActual; }
    public void setIngresosMesActual(BigDecimal ingresosMesActual) { this.ingresosMesActual = ingresosMesActual; }

    public BigDecimal getIngresosMesAnterior() { return ingresosMesAnterior; }
    public void setIngresosMesAnterior(BigDecimal ingresosMesAnterior) { this.ingresosMesAnterior = ingresosMesAnterior; }

    public double getCrecimientoIngresos() { return crecimientoIngresos; }
    public void setCrecimientoIngresos(double crecimientoIngresos) { this.crecimientoIngresos = crecimientoIngresos; }
}