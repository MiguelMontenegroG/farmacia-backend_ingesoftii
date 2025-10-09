package com.farmacia.dto;

import lombok.Data;

@Data
public class PaymentDetailsDTO {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardholderName;

    public PaymentDetailsDTO() {}

    public PaymentDetailsDTO(String cardNumber, String expiryDate, String cvv, String cardholderName) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
    }
}