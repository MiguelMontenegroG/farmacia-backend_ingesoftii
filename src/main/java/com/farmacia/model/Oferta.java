package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ofertas")
public class Oferta {
    @Id
    private String id;

    private String nombre;
    private String descripcion;
    private String tipo; // DESCUENTO_PORCENTAJE, DESCUENTO_FIJO, DOS_POR_UNO
    private Float valor;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean activo;
    private List<String> productosAplicables;
    private List<String> categoriasAplicables;

    // Constructores
    public Oferta() {}

    public Oferta(String nombre, String tipo, Float valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.activo = true;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Float getValor() { return valor; }
    public void setValor(Float valor) { this.valor = valor; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<String> getProductosAplicables() { return productosAplicables; }
    public void setProductosAplicables(List<String> productosAplicables) { this.productosAplicables = productosAplicables; }

    public List<String> getCategoriasAplicables() { return categoriasAplicables; }
    public void setCategoriasAplicables(List<String> categoriasAplicables) { this.categoriasAplicables = categoriasAplicables; }
}