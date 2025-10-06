package com.farmacia.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
@Setter
@Getter
@Document(collection = "productos")
public class Producto {
    // Getters y Setters

    @Id
    private String id;
    @Getter
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal precioOferta;
    private boolean enOferta;
    private String marca;
    private String presentacion;
    @DBRef
    private Categoria categoria;

    private String imagenUrl;
    private int stock;
    private boolean activo;
    private String laboratorio;
    private String principioActivo;
    private String codigoBarras;
    private boolean requiereReceta;

    // Constructores
    public Producto() {
        this.activo = true;
    }

    public Producto(String nombre, String descripcion, BigDecimal precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.activo = true;
    }

}