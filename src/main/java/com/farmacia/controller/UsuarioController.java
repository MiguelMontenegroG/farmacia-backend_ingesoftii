package com.farmacia.controller;


import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    //Verifica el código de activación enviado al correo
    @PostMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigo(@RequestParam String email, @RequestParam String codigo) {
        boolean verificado = usuarioServicio.verificarCodigo(email, codigo);
        if (verificado) {
            return ResponseEntity.ok("Cuenta verificada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(" Código inválido o expirado. Inténtalo nuevamente.");
        }
    }

    // USUARIO NORMAL
    @GetMapping("/perfil/{email}")
    public Usuario obtenerPerfil(@PathVariable String email) {
        return usuarioServicio.obtenerPerfil(email);
    }

    @PutMapping("/actualizar/{id}")
    public Usuario actualizarUsuario(@PathVariable String id, @RequestBody Usuario datos) {
        return usuarioServicio.actualizarUsuario(id, datos);
    }

    @PutMapping("/cambiar-password/{id}")
    public ResponseEntity<String> cambiarPassword(@PathVariable String id, @RequestBody Map<String, String> body) {
        String nuevaPassword = body.get("password");
        if (usuarioServicio.cambiarPassword(id, nuevaPassword)) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        }
        return ResponseEntity.badRequest().body("No se pudo cambiar la contraseña.");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCuentaPropia(@PathVariable String id) {
        usuarioServicio.eliminarCuentaPropia(id);
        return ResponseEntity.ok("Cuenta eliminada correctamente.");
    }


    //ADMINISTRADOR
    @GetMapping("/todos")
    public List<Usuario> listarUsuarios() {
        return usuarioServicio.listarUsuarios();
    }

    @PutMapping("/cambiar-rol/{id}")
    public Usuario cambiarRol(@PathVariable String id, @RequestBody Map<String, String> body) {
        String nuevoRol = body.get("rol");
        return usuarioServicio.cambiarRol(id, nuevoRol);
    }

    @DeleteMapping("/eliminar-admin/{id}")
    public ResponseEntity<String> eliminarUsuarioPorAdmin(@PathVariable String id) {
        usuarioServicio.eliminarUsuarioPorAdmin(id);
        return ResponseEntity.ok("Usuario eliminado correctamente por el administrador.");
    }

}