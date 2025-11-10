package com.farmacia.security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // En el entorno académico, se ha eliminado la funcionalidad JWT
    // Esta clase se mantiene como un componente vacío para evitar errores de compilación
    // en otras partes del código que puedan depender de ella

    public String generateToken(String username, String role, String userId) {
        // En entorno académico, no generamos tokens reales
        return "dummy-token-for-academic-environment";
    }

    public String extractUsername(String token) {
        // En entorno académico, devolvemos un nombre de usuario fijo
        return "usuario-prueba";
    }

    public String extractRole(String token) {
        // En entorno académico, devolvemos un rol fijo
        return "CLIENTE";
    }

    public String extractUserId(String token) {
        // En entorno académico, devolvemos un ID de usuario fijo
        return "usuario-prueba-id";
    }

    public boolean isTokenExpired(String token) {
        // En entorno académico, los tokens nunca expiran
        return false;
    }

    public boolean validateToken(String token, String username) {
        // En entorno académico, todos los tokens son válidos
        return true;
    }
}