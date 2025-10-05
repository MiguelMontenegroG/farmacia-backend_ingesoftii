
package com.farmacia.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "carritos")
public class Carrito {

    @Id
    private String id;
    private String usuarioId;
    private List<ItemCarrito> items;
    private BigDecimal subtotal;
    private BigDecimal total;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Carrito() {
        this.items = new ArrayList<>();
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.subtotal = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    public Carrito(String usuarioId) {
        this();
        this.usuarioId = usuarioId;
    }

    public void agregarItem(Producto producto, int cantidad) {
        ItemCarrito itemExistente = buscarItem(producto.getId());

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
            itemExistente.calcularSubtotal();
        } else {
            ItemCarrito nuevoItem = new ItemCarrito(producto, cantidad);
            items.add(nuevoItem);
        }

        actualizarTotales();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void actualizarCantidadItem(String productoId, int cantidad) {
        ItemCarrito item = buscarItem(productoId);

        if (item == null) {
            throw new RuntimeException("Item no encontrado en el carrito");
        }

        if (cantidad <= 0) {
            items.remove(item);
        } else {
            item.setCantidad(cantidad);
            item.calcularSubtotal();
        }

        actualizarTotales();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void eliminarItem(String productoId) {
        ItemCarrito item = buscarItem(productoId);

        if (item != null) {
            items.remove(item);
            actualizarTotales();
            this.fechaActualizacion = LocalDateTime.now();
        }
    }

    public void limpiar() {
        this.items.clear();
        this.subtotal = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.fechaActualizacion = LocalDateTime.now();
    }

    private ItemCarrito buscarItem(String productoId) {
        return items.stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst()
                .orElse(null);
    }

    private void actualizarTotales() {
        this.subtotal = items.stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal;
    }

    public int getCantidadTotalItems() {
        return items.stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }
}
