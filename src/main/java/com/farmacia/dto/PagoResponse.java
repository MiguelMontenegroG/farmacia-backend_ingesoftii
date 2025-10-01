package com.farmacia.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoResponse {
    private String id;
    private String estado;
    private BigDecimal total;
    private BigDecimal subtotal;
    private BigDecimal impuestos;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String numeroTransaccion;
    private String mensaje;

    // Constructores
    public PagoResponse() {}

    public PagoResponse(String estado, BigDecimal total, String mensaje) {
        this.estado = estado;
        this.total = total;
        this.mensaje = mensaje;
        this.fechaPago = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public String getNumeroTransaccion() { return numeroTransaccion; }
    public void setNumeroTransaccion(String numeroTransaccion) { this.numeroTransaccion = numeroTransaccion; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}