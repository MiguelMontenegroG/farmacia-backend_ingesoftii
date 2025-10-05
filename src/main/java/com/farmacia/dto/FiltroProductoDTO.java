package com.farmacia.dto;


import lombok.Data;

@Data
public class FiltroProductoDTO {
    private String busqueda;
    private String categoriaId;
    private Double precioMin;
    private Double precioMax;
    private Boolean requiereReceta;
    private Boolean activo;
    private String ordenarPor;
}
