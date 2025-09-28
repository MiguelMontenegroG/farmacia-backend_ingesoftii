package com.farmacia.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}