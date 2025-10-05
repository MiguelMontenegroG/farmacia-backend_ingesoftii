package com.farmacia.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class ActualizarCantidadDTO {

    @NotBlank(message = "El ID del producto es requerido")
    private String productoId;

    @Min(value = 0, message = "La cantidad debe ser al menos 0")
    private int cantidad;

    public ActualizarCantidadDTO() {}

    public ActualizarCantidadDTO(String productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}