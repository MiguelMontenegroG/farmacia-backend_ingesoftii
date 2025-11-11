package com.farmacia.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemCarrito {

    private String id; // ID único del item en el carrito
    private String productoId;
    private String nombre;
    private String marca;
    private String presentacion;
    private BigDecimal precio;
    private BigDecimal precioOferta;
    private int cantidad;
    private BigDecimal subtotal;
    private String imagen;
    private int stock;
    private boolean requiereReceta;
    private LocalDateTime fechaAgregado;

    public ItemCarrito() {
        this.fechaAgregado = LocalDateTime.now();
        this.precio = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
    }

    public ItemCarrito(Producto producto, int cantidad) {
        this();
        this.id = java.util.UUID.randomUUID().toString();
        this.productoId = producto.getId();
        this.nombre = producto.getNombre();
        this.marca = producto.getMarca();
        this.presentacion = producto.getPresentacion();
        this.precio = producto.getPrecio();
        this.precioOferta = producto.getPrecioOferta();
        this.cantidad = cantidad;
        this.imagen = producto.getImagen();
        this.stock = producto.getStock();
        this.requiereReceta = producto.isRequiereReceta();
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        BigDecimal precioFinal = (this.precioOferta != null && this.precioOferta.compareTo(BigDecimal.ZERO) > 0)
                ? this.precioOferta
                : this.precio;
        this.subtotal = precioFinal.multiply(BigDecimal.valueOf(this.cantidad));
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
