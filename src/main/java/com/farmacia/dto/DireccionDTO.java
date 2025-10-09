package com.farmacia.dto;

import lombok.Data;

@Data
public class DireccionDTO {
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String phone; // opcional para billing

    public DireccionDTO() {}

    public DireccionDTO(String fullName, String street, String city, String state, String zipCode, String phone) {
        this.fullName = fullName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
    }
}