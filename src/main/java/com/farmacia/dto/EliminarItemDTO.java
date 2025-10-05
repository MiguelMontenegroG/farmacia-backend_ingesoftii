package com.farmacia.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class EliminarItemDTO {

    @NotBlank(message = "El ID del producto es requerido")
    private String productoId;

    public EliminarItemDTO() {}

    public EliminarItemDTO(String productoId) {
        this.productoId = productoId;
    }
}