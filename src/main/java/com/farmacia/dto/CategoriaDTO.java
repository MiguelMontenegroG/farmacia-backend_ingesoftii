package com.farmacia.dto;

import java.util.List;

public class CategoriaDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String categoriaPadreId;
    private String categoriaPadreNombre;
    private List<String> keywords;
    private int orden;
    private boolean esCategoriaRaiz;

    // Constructores
    public CategoriaDTO() {}

    public CategoriaDTO(String id, String nombre, String descripcion, String imagenUrl,
                        String categoriaPadreId, String categoriaPadreNombre,
                        List<String> keywords, int orden, boolean esCategoriaRaiz) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.categoriaPadreId = categoriaPadreId;
        this.categoriaPadreNombre = categoriaPadreNombre;
        this.keywords = keywords;
        this.orden = orden;
        this.esCategoriaRaiz = esCategoriaRaiz;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getCategoriaPadreId() { return categoriaPadreId; }
    public void setCategoriaPadreId(String categoriaPadreId) { this.categoriaPadreId = categoriaPadreId; }

    public String getCategoriaPadreNombre() { return categoriaPadreNombre; }
    public void setCategoriaPadreNombre(String categoriaPadreNombre) { this.categoriaPadreNombre = categoriaPadreNombre; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    public boolean isEsCategoriaRaiz() { return esCategoriaRaiz; }
    public void setEsCategoriaRaiz(boolean esCategoriaRaiz) { this.esCategoriaRaiz = esCategoriaRaiz; }
}