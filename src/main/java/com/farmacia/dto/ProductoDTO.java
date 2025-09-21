package com.farmacia.dto;

import java.math.BigDecimal;

public class ProductoDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal precioOferta;
    private boolean enOferta;
    private String categoriaId;
    private String categoriaNombre;
    private String imagenUrl;
    private int stock;
    private String laboratorio;
    private String principioActivo;
    private String codigoBarras;
    private boolean requiereReceta;

    // Constructores
    public ProductoDTO() {}

    public ProductoDTO(String id, String nombre, String descripcion, BigDecimal precio,
                       BigDecimal precioOferta, boolean enOferta, String categoriaId,
                       String categoriaNombre, String imagenUrl, int stock,
                       String laboratorio, String principioActivo, String codigoBarras,
                       boolean requiereReceta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioOferta = precioOferta;
        this.enOferta = enOferta;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.imagenUrl = imagenUrl;
        this.stock = stock;
        this.laboratorio = laboratorio;
        this.principioActivo = principioActivo;
        this.codigoBarras = codigoBarras;
        this.requiereReceta = requiereReceta;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public BigDecimal getPrecioOferta() { return precioOferta; }
    public void setPrecioOferta(BigDecimal precioOferta) { this.precioOferta = precioOferta; }

    public boolean isEnOferta() { return enOferta; }
    public void setEnOferta(boolean enOferta) { this.enOferta = enOferta; }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }

    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public String getPrincipioActivo() { return principioActivo; }
    public void setPrincipioActivo(String principioActivo) { this.principioActivo = principioActivo; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public boolean isRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(boolean requiereReceta) { this.requiereReceta = requiereReceta; }
}