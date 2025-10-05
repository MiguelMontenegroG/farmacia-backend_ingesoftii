package com.farmacia.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ItemCarrito {

    private String productoId;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;
    private double subtotal;
    private String imagenUrl;
    private LocalDateTime fechaAgregado;

    public ItemCarrito() {
        this.fechaAgregado = LocalDateTime.now();
    }

    public ItemCarrito(Producto producto, int cantidad) {
        this();
        this.productoId = producto.getId();
        this.nombreProducto = producto.getNombre();
        this.precioUnitario = producto.getPrecio();
        this.cantidad = cantidad;
        this.imagenUrl = producto.getImagenUrl();
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
    }
}