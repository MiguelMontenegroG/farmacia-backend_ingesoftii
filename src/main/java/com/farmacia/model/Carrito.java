package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "carritos")
public class Carrito {
    @Id
    private String id;

    @Indexed
    private String usuarioId;

    private List<ItemCarrito> items;
    private Float subtotal;
    private Float descuento;
    private Float total;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private boolean activo;

    // Constructores
    public Carrito() {}

    public Carrito(String usuarioId) {
        this.usuarioId = usuarioId;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        this.subtotal = 0.0f;
        this.descuento = 0.0f;
        this.total = 0.0f;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public List<ItemCarrito> getItems() { return items; }
    public void setItems(List<ItemCarrito> items) { this.items = items; }

    public Float getSubtotal() { return subtotal; }
    public void setSubtotal(Float subtotal) { this.subtotal = subtotal; }

    public Float getDescuento() { return descuento; }
    public void setDescuento(Float descuento) { this.descuento = descuento; }

    public Float getTotal() { return total; }
    public void setTotal(Float total) { this.total = total; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}