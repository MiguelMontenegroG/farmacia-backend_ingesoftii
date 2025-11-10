package com.farmacia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ComentarioDTO {
    private String id;
    
    @NotBlank(message = "ID del producto es requerido")
    private String productoId;
    
    @NotBlank(message = "ID del usuario es requerido")
    private String usuarioId;
    
    @NotBlank(message = "Nombre del usuario es requerido")
    @Size(min = 2, max = 50, message = "Nombre debe tener entre 2 y 50 caracteres")
    private String nombreUsuario;
    
    @Min(value = 1, message = "Calificación mínima es 1")
    @Max(value = 5, message = "Calificación máxima es 5")
    private Integer calificacion;
    
    @Size(max = 100, message = "Título no puede exceder 100 caracteres")
    private String titulo;
    
    @NotBlank(message = "Contenido es requerido")
    @Size(min = 10, max = 1000, message = "Contenido debe tener entre 10 y 1000 caracteres")
    private String contenido;
    
    private LocalDateTime fechaCreacion;
    private boolean activo;
    private boolean flagedAsSpam;
    private Integer reportesSpam;

    public ComentarioDTO() {}

    public ComentarioDTO(String productoId, String usuarioId, String nombreUsuario, 
                        Integer calificacion, String titulo, String contenido) {
        this.productoId = productoId;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.calificacion = calificacion;
        this.titulo = titulo;
        this.contenido = contenido;
        this.activo = true;
        this.flagedAsSpam = false;
        this.reportesSpam = 0;
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

    public boolean isFlaggedAsSpam() { return flagedAsSpam; }
    public void setFlaggedAsSpam(boolean flagedAsSpam) { this.flagedAsSpam = flagedAsSpam; }

    public Integer getReportesSpam() { return reportesSpam; }
    public void setReportesSpam(Integer reportesSpam) { this.reportesSpam = reportesSpam; }
}