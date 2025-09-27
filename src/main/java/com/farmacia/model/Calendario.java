package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "calendario")
public class Calendario {
    @Id
    private String id;

    private String usuarioId;
    private String pedidoId;
    private String tipoEvento; // ENTREGA, RECORDATORIO_COMPRA, CITA_MEDICA
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaEvento;
    private boolean completado;
    private boolean notificacionEnviada;

    // Constructores
    public Calendario() {}

    public Calendario(String usuarioId, String tipoEvento, String titulo, LocalDateTime fechaEvento) {
        this.usuarioId = usuarioId;
        this.tipoEvento = tipoEvento;
        this.titulo = titulo;
        this.fechaEvento = fechaEvento;
        this.completado = false;
        this.notificacionEnviada = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDateTime fechaEvento) { this.fechaEvento = fechaEvento; }

    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }

    public boolean isNotificacionEnviada() { return notificacionEnviada; }
    public void setNotificacionEnviada(boolean notificacionEnviada) { this.notificacionEnviada = notificacionEnviada; }
}