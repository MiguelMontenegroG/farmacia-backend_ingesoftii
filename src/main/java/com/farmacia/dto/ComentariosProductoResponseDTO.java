package com.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ComentariosProductoResponseDTO {
    private List<ComentarioDTO> comentarios;
    private Double calificacionPromedio;
    private Integer total;

    public ComentariosProductoResponseDTO(List<ComentarioDTO> comentarios, Double calificacionPromedio, Integer total) {
        this.comentarios = comentarios;
        this.calificacionPromedio = calificacionPromedio;
        this.total = total;
    }
}