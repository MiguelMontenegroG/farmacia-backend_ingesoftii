package com.farmacia.dto;

import java.math.BigDecimal;

public class ProductoReporteDTO {
    private String nombre;
    private int stock;
    private int stockMinimo;
    private BigDecimal precio;
    private String categoria;

    // Constructores
    public ProductoReporteDTO() {}

    public ProductoReporteDTO(String nombre, int stock, int stockMinimo,
                              BigDecimal precio, String categoria) {
        this.nombre = nombre;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.precio = precio;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
