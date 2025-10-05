package com.farmacia.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EliminarItemDTO {
    // Getters y Setters
    private String usuarioId;
    private String productoId;

    public EliminarItemDTO() {}

    public EliminarItemDTO(String productoId) {
        this.productoId = productoId;
    }

}