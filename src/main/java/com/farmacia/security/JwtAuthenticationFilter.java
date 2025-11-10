package com.farmacia.security;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // En el entorno académico, se ha eliminado la funcionalidad JWT
    // Esta clase se mantiene como un componente vacío para evitar errores de compilación
    // en otras partes del código que puedan depender de ella

    // Clase auxiliar para mantener compatibilidad con el código existente
    public static class CustomUserDetails {
        private String username;
        private String userId;

        public CustomUserDetails(String username, String userId) {
            this.username = username;
            this.userId = userId;
        }

        public String getId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }
    }
}