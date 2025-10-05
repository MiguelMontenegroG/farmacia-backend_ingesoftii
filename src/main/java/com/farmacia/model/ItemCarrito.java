package com.farmacia.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ItemCarrito {

    private String productoId;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private int cantidad;
    private BigDecimal subtotal;
    private String imagenUrl;
    private LocalDateTime fechaAgregado;

    public ItemCarrito() {
        this.fechaAgregado = LocalDateTime.now();
        this.precioUnitario = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
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
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
}
