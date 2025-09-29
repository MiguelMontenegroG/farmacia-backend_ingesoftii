package com.farmacia.dto;

public class ActualizarStockRequest {
    private int cantidad;
    private String motivo;

    // Constructores
    public ActualizarStockRequest() {}

    public ActualizarStockRequest(int cantidad, String motivo) {
        this.cantidad = cantidad;
        this.motivo = motivo;
    }

    // Getters y Setters
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
