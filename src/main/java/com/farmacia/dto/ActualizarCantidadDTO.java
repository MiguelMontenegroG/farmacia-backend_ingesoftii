package com.farmacia.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}