package com.farmacia.model;

public class ItemVenta {
    private String productoId;
    private String nombreProducto;
    private Float precioUnitario;
    private Integer cantidad;
    private Float subtotal;
    private Float descuentoItem;

    // Constructores
    public ItemVenta() {}

    public ItemVenta(String productoId, String nombreProducto, Float precioUnitario, Integer cantidad) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = precioUnitario * cantidad;
        this.descuentoItem = 0.0f;
    }

    // Getters y Setters
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Float getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Float precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Float getSubtotal() { return subtotal; }
    public void setSubtotal(Float subtotal) { this.subtotal = subtotal; }

    public Float getDescuentoItem() { return descuentoItem; }
    public void setDescuentoItem(Float descuentoItem) { this.descuentoItem = descuentoItem; }
}