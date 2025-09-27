package com.farmacia.model;

import java.time.LocalDateTime;

public class Pago {
    private String id;
    private String metodoPago;
    private String estado;
    private Float monto;
    private String numeroTransaccion;
    private LocalDateTime fechaPago;
    private String entidadFinanciera;
    private String numeroReferencia;

    // Constructores
    public Pago() {}

    public Pago(String metodoPago, Float monto) {
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.estado = "PENDIENTE";
        this.fechaPago = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Float getMonto() { return monto; }
    public void setMonto(Float monto) { this.monto = monto; }

    public String getNumeroTransaccion() { return numeroTransaccion; }
    public void setNumeroTransaccion(String numeroTransaccion) { this.numeroTransaccion = numeroTransaccion; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public String getEntidadFinanciera() { return entidadFinanciera; }
    public void setEntidadFinanciera(String entidadFinanciera) { this.entidadFinanciera = entidadFinanciera; }

    public String getNumeroReferencia() { return numeroReferencia; }
    public void setNumeroReferencia(String numeroReferencia) { this.numeroReferencia = numeroReferencia; }
}
