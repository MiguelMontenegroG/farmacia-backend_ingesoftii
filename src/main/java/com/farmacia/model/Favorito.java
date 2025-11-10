package com.farmacia.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "favoritos")
public class Favorito {

    @Id
    private String id;

    private String usuarioId;
    private String productoId;

    @CreatedDate
    private LocalDateTime fechaAgregado;

    // Constructor vacío
    public Favorito() {}

    // Constructor con parámetros
    public Favorito(String usuarioId, String productoId) {
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.fechaAgregado = LocalDateTime.now();
    }
}