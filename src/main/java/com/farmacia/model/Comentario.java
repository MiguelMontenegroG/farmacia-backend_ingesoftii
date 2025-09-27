package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "comentarios")
public class Comentario {
    @Id
    private String id;

    @Indexed
    private String productoId;

    @Indexed
    private String usuarioId;

    private String nombreUsuario;
    private Integer calificacion; // 1-5 estrellas
    private String titulo;
    private String contenido;
    private LocalDateTime fechaCreacion;
    private boolean activo;

    // Constructores
    public Comentario() {}

    public Comentario(String productoId, String usuarioId, String nombreUsuario, Integer calificacion, String contenido) {
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.calificacion = calificacion;
        this.contenido = contenido;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}