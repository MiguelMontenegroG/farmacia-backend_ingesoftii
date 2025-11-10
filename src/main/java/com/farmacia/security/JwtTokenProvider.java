package com.farmacia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Genera un token JWT para el usuario
     * En entorno académico, esto simplemente delega a JwtUtil
     */
    public String generateToken(String username, String role, String userId) {
        return jwtUtil.generateToken(username, role, userId);
    }

    /**
     * Extrae el nombre de usuario del token
     */
    public String getUsernameFromJWT(String token) {
        return jwtUtil.extractUsername(token);
    }

    /**
     * Extrae el rol del token
     */
    public String getRoleFromJWT(String token) {
        return jwtUtil.extractRole(token);
    }

    /**
     * Extrae el ID de usuario del token
     */
    public String getUserIdFromJWT(String token) {
        return jwtUtil.extractUserId(token);
    }

    /**
     * Valida el token
     */
    public boolean validateToken(String token) {
        // En entorno académico, todos los tokens son válidos
        return true;
    }

    /**
     * Verifica si el token ha expirado
     */
    public boolean isTokenExpired(String token) {
        return jwtUtil.isTokenExpired(token);
    }

    /**
     * Valida el token contra un nombre de usuario específico
     */
    public boolean validateToken(String token, String username) {
        return jwtUtil.validateToken(token, username);
    }
}