package com.farmacia.dto;

import lombok.Data;

@Data
public class CheckoutItemDTO {
    private String productId;
    private Integer quantity;

    public CheckoutItemDTO() {}

    public CheckoutItemDTO(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}