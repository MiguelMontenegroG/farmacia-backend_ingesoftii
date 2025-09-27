package com.farmacia.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ventas")
public class Venta {
    @Id
    private String id;

    @Indexed
    private String pedidoId;

    @Indexed
    private String usuarioId;

    private String numeroVenta;
    private List<ItemVenta> items;
    private Float subtotal;
    private Float descuento;
    private Float impuestos;
    private Float total;
    private String estado; // COMPLETADA, CANCELADA, REEMBOLSADA
    private LocalDateTime fechaVenta;
    private String metodoPago;
    private String vendedorId; // ID del administrador que procesó la venta

    // Constructores
    public Venta() {}

    public Venta(String pedidoId, String usuarioId, String numeroVenta) {
        this.pedidoId = pedidoId;
        this.usuarioId = usuarioId;
        this.numeroVenta = numeroVenta;
        this.fechaVenta = LocalDateTime.now();
        this.estado = "COMPLETADA";
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getNumeroVenta() { return numeroVenta; }
    public void setNumeroVenta(String numeroVenta) { this.numeroVenta = numeroVenta; }

    public List<ItemVenta> getItems() { return items; }
    public void setItems(List<ItemVenta> items) { this.items = items; }

    public Float getSubtotal() { return subtotal; }
    public void setSubtotal(Float subtotal) { this.subtotal = subtotal; }

    public Float getDescuento() { return descuento; }
    public void setDescuento(Float descuento) { this.descuento = descuento; }

    public Float getImpuestos() { return impuestos; }
    public void setImpuestos(Float impuestos) { this.impuestos = impuestos; }

    public Float getTotal() { return total; }
    public void setTotal(Float total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getVendedorId() { return vendedorId; }
    public void setVendedorId(String vendedorId) { this.vendedorId = vendedorId; }
}
