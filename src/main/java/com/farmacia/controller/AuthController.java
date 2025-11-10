package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.service.UsuarioService;
import com.farmacia.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://*.vercel.app"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        try {
            usuarioService.logout();
            return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.success("Logout processed", null));
        }
    }
}