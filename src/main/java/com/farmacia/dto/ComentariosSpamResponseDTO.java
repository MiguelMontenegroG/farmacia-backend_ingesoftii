package com.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentariosSpamResponseDTO {
    private List<ComentarioDTO> comentarios;
    private Integer total;
}