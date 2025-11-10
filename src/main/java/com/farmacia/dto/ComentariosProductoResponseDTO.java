package com.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentariosProductoResponseDTO {
    private List<ComentarioDTO> comentarios;
    private Double calificacionPromedio;
    private Integer total;
}