package com.farmacia.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "notificaciones")
public class Notificacion {

    @Id
    private String id;

    private String usuarioId;
    private String titulo;
    private String mensaje;
    private String tipo; // PEDIDO, STOCK, PROMOCION, ALERTA, SISTEMA
    private boolean leida = false;

    @CreatedDate
    private LocalDateTime fechaCreacion;

    // Constructor vacío
    public Notificacion() {}

    // Constructor con parámetros
    public Notificacion(String usuarioId, String titulo, String mensaje, String tipo) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fechaCreacion = LocalDateTime.now();
    }
}