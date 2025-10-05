// ========== ITEM CARRITO ==========
package com.farmacia.model;

public class ItemCarrito {
    private String productoId;
    private String nombreProducto;
    private Float precio;
    private Integer cantidad;
    private Float subtotal;
    private String imagenUrl;

    // Constructores
    public ItemCarrito() {}

    public ItemCarrito(String productoId, String nombreProducto, Float precio, Integer cantidad) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = precio * cantidad;
    }

    // Getters y Setters
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Float getPrecio() { return precio; }
    public void setPrecio(Float precio) { this.precio = precio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Float getSubtotal() { return subtotal; }
    public void setSubtotal(Float subtotal) { this.subtotal = subtotal; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
