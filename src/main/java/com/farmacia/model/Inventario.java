package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "inventario")
public class Inventario {
    @Id
    private String id;

    @Indexed
    private String productoId;

    private Integer stockActual;
    private Integer stockMinimo;
    private Integer stockMaximo;
    private LocalDateTime fechaUltimaActualizacion;
    private String tipoMovimiento; // ENTRADA, SALIDA, AJUSTE
    private Integer cantidad;
    private String motivo;
    private String usuarioId; // Quien hizo el movimiento

    // Constructores
    public Inventario() {}

    public Inventario(String productoId, Integer stockActual) {
        this.productoId = productoId;
        this.stockActual = stockActual;
        this.fechaUltimaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public Integer getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(Integer stockMaximo) { this.stockMaximo = stockMaximo; }

    public LocalDateTime getFechaUltimaActualizacion() { return fechaUltimaActualizacion; }
    public void setFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) { this.fechaUltimaActualizacion = fechaUltimaActualizacion; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}