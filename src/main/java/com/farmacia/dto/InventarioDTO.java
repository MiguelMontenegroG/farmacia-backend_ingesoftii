package com.farmacia.dto;

import com.farmacia.model.Categoria;

public class InventarioDTO {
    private String id;
    private String nombre;
    private int stock;
    private Categoria categoria;

    // Constructores
    public InventarioDTO() {}

    public InventarioDTO(String id, String nombre, int stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
