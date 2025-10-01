package com.farmacia.dto;

import java.math.BigDecimal;

public class ItemVentaDTO {
    private String productoId;
    private String nombreProducto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private boolean requiereReceta;

    // Constructores
    public ItemVentaDTO() {}

    public ItemVentaDTO(String productoId, String nombreProducto, int cantidad,
                        BigDecimal precioUnitario, boolean requiereReceta) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.requiereReceta = requiereReceta;
    }

    // Getters y Setters
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public boolean isRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(boolean requiereReceta) { this.requiereReceta = requiereReceta; }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
