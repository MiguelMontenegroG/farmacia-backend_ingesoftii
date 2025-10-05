package com.farmacia.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResumenDTO {
    private int cantidadItems;
    private double subtotal;
    private double total;
    private boolean tieneItems;
}
