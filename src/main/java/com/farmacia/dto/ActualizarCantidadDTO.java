package com.farmacia.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActualizarCantidadDTO {
    // Getters y Setters
    private String usuarioId;
    private String productoId;
    private int cantidad;

    public ActualizarCantidadDTO() {}

    public ActualizarCantidadDTO(String productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

}