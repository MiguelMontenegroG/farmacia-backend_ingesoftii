package com.farmacia.controller;

import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        usuarioService.logout();
        return ResponseEntity.ok("Logout successful");
    }
}