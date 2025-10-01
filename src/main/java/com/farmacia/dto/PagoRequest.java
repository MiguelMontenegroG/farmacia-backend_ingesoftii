package com.farmacia.dto;

import java.util.List;

public class PagoRequest {
    private List<ItemVentaDTO> items;
    private String metodoPago;
    private String numeroTarjeta; // Opcional, para pagos con tarjeta
    private String clienteNombre;
    private String clienteEmail;
    private String seguroMedico; // Opcional, para farmacias
    private String recetaMedica; // Opcional, si requiere receta

    // Constructores, Getters y Setters
    public PagoRequest() {}

    // Getters y Setters para todos los campos
    public List<ItemVentaDTO> getItems() { return items; }
    public void setItems(List<ItemVentaDTO> items) { this.items = items; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getSeguroMedico() { return seguroMedico; }
    public void setSeguroMedico(String seguroMedico) { this.seguroMedico = seguroMedico; }

    public String getRecetaMedica() { return recetaMedica; }
    public void setRecetaMedica(String recetaMedica) { this.recetaMedica = recetaMedica; }
}