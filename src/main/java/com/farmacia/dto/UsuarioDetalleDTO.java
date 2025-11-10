package com.farmacia.dto;

import com.farmacia.Enum.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDetalleDTO {

    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private Rol rol;
    private boolean activo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaUltimaActualizacion;
    private Integer totalCompras;
    private Double montoTotalCompras;
    private List<String> prescripciones;
}