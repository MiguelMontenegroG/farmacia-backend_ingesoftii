package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notificaciones")
public class Notificacion {
    @Id
    private String id;

    @Indexed
    private String usuarioId;

    private String tipo; // PEDIDO, PROMOCION, RECORDATORIO, GENERAL
    private String titulo;
    private String mensaje;
    private boolean leida;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLeida;
    private String pedidoId; // Si está relacionada con un pedido

    // Constructores
    public Notificacion() {}

    public Notificacion(String usuarioId, String tipo, String titulo, String mensaje) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaLeida() { return fechaLeida; }
    public void setFechaLeida(LocalDateTime fechaLeida) { this.fechaLeida = fechaLeida; }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }
}