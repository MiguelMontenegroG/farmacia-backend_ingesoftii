package com.farmacia.dto;
import lombok.Data;

@Data
public class AgregarItemDTO {
    private String usuarioId;
    private String productoId;
    private int cantidad;

    public AgregarItemDTO() {}

    public AgregarItemDTO(String productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

}