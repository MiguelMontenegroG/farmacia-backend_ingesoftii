package com.farmacia.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResumenDTO {
    private int cantidadItems;
    private BigDecimal subtotal;
    private BigDecimal total;
    private boolean tieneItems;
}