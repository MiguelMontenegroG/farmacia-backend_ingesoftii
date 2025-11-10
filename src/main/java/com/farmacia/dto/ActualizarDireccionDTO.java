package com.farmacia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarDireccionDTO {

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
    private String direccion;

    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(min = 2, max = 50, message = "La ciudad debe tener entre 2 y 50 caracteres")
    private String ciudad;

    @NotBlank(message = "El estado/provincia no puede estar vacío")
    @Size(min = 2, max = 50, message = "El estado debe tener entre 2 y 50 caracteres")
    private String estado;

    @NotBlank(message = "El código postal no puede estar vacío")
    @Pattern(regexp = "^\\d{5,10}$", message = "El código postal debe contener entre 5 y 10 dígitos")
    private String codigoPostal;
}