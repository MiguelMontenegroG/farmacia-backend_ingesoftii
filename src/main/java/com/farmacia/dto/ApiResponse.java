package com.farmacia.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String mensaje;
    private T data;
    private String error;

    // Constructor para éxito
    public ApiResponse(boolean success, String mensaje, T data) {
        this.success = success;
        this.mensaje = mensaje;
        this.data = data;
    }

    // Constructor para error
    public ApiResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
        this.mensaje = "Error occurred";
    }
    
    // Constructor vacío
    public ApiResponse() {}

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }
}