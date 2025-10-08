package com.farmacia.controller;


import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000") // conexión con React

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioServicio;

    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody RegistroUsuarioDTO datosRegistro) {
        return usuarioServicio.registrarUsuario(datosRegistro);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespuesta> login(@RequestBody LoginDTO loginDTO) {
        LoginRespuesta respuesta = usuarioServicio.iniciarSesion(loginDTO);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/existe/{email}")
    public boolean verificarEmail(@PathVariable String email) {
        return usuarioServicio.existePorEmail(email);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        usuarioServicio.logout();
        return ResponseEntity.ok("Logout successful");
    }
}