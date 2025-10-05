package com.farmacia.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class AgregarItemDTO {

    @NotBlank(message = "El ID del producto es requerido")
    private String productoId;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    public AgregarItemDTO() {}

    public AgregarItemDTO(String productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}