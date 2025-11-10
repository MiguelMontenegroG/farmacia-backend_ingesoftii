package com.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaUsuariosResponseDTO {
    private List<UsuarioDetalleDTO> usuarios;
    private Integer total;
}