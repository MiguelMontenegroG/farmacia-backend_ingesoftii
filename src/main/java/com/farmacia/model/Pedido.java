package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String id;

    @Indexed
    private String usuarioId;

    private String numeroPedido;
    private List<ItemPedido> items;
    private Float subtotal;
    private Float descuento;
    private Float costoEnvio;
    private Float total;
    private String estado; // PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO
    private Direccion direccionEntrega;
    private String metodoPago;
    private String cuponAplicado;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntrega;
    private String observaciones;

    // Constructores
    public Pedido() {}

    public Pedido(String usuarioId, String numeroPedido) {
        this.usuarioId = usuarioId;
        this.numeroPedido = numeroPedido;
        this.fechaPedido = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public List<ItemPedido> getItems() { return items; }
    public void setItems(List<ItemPedido> items) { this.items = items; }

    public Float getSubtotal() { return subtotal; }
    public void setSubtotal(Float subtotal) { this.subtotal = subtotal; }

    public Float getDescuento() { return descuento; }
    public void setDescuento(Float descuento) { this.descuento = descuento; }

    public Float getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(Float costoEnvio) { this.costoEnvio = costoEnvio; }

    public Float getTotal() { return total; }
    public void setTotal(Float total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Direccion getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(Direccion direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getCuponAplicado() { return cuponAplicado; }
    public void setCuponAplicado(String cuponAplicado) { this.cuponAplicado = cuponAplicado; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

