package com.farmacia.dto;

import lombok.Data;
import java.util.List;

@Data
public class CheckoutDataDTO {
    private List<CheckoutItemDTO> items;
    private DireccionDTO shippingAddress;
    private DireccionDTO billingAddress;
    private String paymentMethod; // "credit_card", "debit_card", "cash_on_delivery"
    private PaymentDetailsDTO paymentDetails; // opcional

    public CheckoutDataDTO() {}

    public CheckoutDataDTO(List<CheckoutItemDTO> items, DireccionDTO shippingAddress,
                          DireccionDTO billingAddress, String paymentMethod, PaymentDetailsDTO paymentDetails) {
        this.items = items;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
    }
}