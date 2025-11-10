package com.farmacia.controller;


import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.dto.ActualizarPerfilDTO;
import com.farmacia.dto.ActualizarDireccionDTO;
import com.farmacia.dto.UsuarioDetalleDTO;
import com.farmacia.dto.ActualizarUsuarioAdminDTO;
import com.farmacia.dto.ApiResponse;
import com.farmacia.dto.ListaUsuariosResponseDTO;
import com.farmacia.model.Usuario;
import com.farmacia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://*.vercel.app"}, allowCredentials = "true")

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioServicio;

    // ============ Endpoints de Autenticación ============
    
    @PostMapping("/registro")
    public ResponseEntity<ApiResponse> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO datosRegistro) {
        try {
            Usuario usuario = usuarioServicio.registrarUsuario(datosRegistro);
            ApiResponse response = new ApiResponse(true, "Usuario registrado exitosamente", usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al registrar usuario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            LoginRespuesta respuesta = usuarioServicio.iniciarSesion(loginDTO);
            ApiResponse response = new ApiResponse(true, "Sesión iniciada exitosamente", respuesta);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al iniciar sesión: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/existe/{email}")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) {
        boolean existe = usuarioServicio.existePorEmail(email);
        return ResponseEntity.ok(existe);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        usuarioServicio.logout();
        ApiResponse response = new ApiResponse(true, "Sesión cerrada exitosamente", null);
        return ResponseEntity.ok(response);
    }
    
    // ============ Endpoints para Gestión de Cuenta (Cliente) ============
    
    /**
     * Obtener perfil del usuario
     * GET /api/usuarios/{id}/perfil
     */
    @GetMapping("/{id}/perfil")
    public ResponseEntity<ApiResponse> obtenerPerfilUsuario(@PathVariable String id) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
            ApiResponse response = new ApiResponse(true, "Perfil obtenido exitosamente", usuario);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Actualizar perfil de usuario (datos personales)
     * PUT /api/usuarios/{id}/perfil
     */
    @PutMapping("/{id}/perfil")
    public ResponseEntity<ApiResponse> actualizarPerfil(
            @PathVariable String id,
            @Valid @RequestBody ActualizarPerfilDTO perfilDTO) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarPerfil(id, perfilDTO);
            ApiResponse response = new ApiResponse(true, "Perfil actualizado exitosamente", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al actualizar perfil: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Actualizar dirección de entrega
     * PUT /api/usuarios/{id}/direccion
     */
    @PutMapping("/{id}/direccion")
    public ResponseEntity<ApiResponse> actualizarDireccion(
            @PathVariable String id,
            @Valid @RequestBody ActualizarDireccionDTO direccionDTO) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarDireccion(id, direccionDTO);
            ApiResponse response = new ApiResponse(true, "Dirección actualizada exitosamente", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al actualizar dirección: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    // ============ Endpoints para Gestión de Usuarios (Admin) ============
    
    /**
     * Listar todos los usuarios (Admin)
     * GET /api/usuarios/admin/lista
     */
    @GetMapping("/admin/lista")
    public ResponseEntity<ApiResponse> listarUsuarios() {
        try {
            List<UsuarioDetalleDTO> usuariosList = usuarioServicio.listarUsuarios();
            ListaUsuariosResponseDTO responseData = new ListaUsuariosResponseDTO(
                usuariosList,
                usuariosList.size()
            );
            ApiResponse response = new ApiResponse(
                true, 
                "Usuarios obtenidos exitosamente",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al listar usuarios: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Listar clientes activos (Admin)
     * GET /api/usuarios/admin/clientes-activos
     */
    @GetMapping("/admin/clientes-activos")
    public ResponseEntity<ApiResponse> listarClientesActivos() {
        try {
            List<UsuarioDetalleDTO> usuariosList = usuarioServicio.listarClientesActivos();
            ListaUsuariosResponseDTO responseData = new ListaUsuariosResponseDTO(
                usuariosList,
                usuariosList.size()
            );
            ApiResponse response = new ApiResponse(
                true, 
                "Clientes activos obtenidos",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al listar clientes: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Buscar usuarios por nombre o apellido (Admin)
     * GET /api/usuarios/admin/buscar?termino=nombre
     */
    @GetMapping("/admin/buscar")
    public ResponseEntity<ApiResponse> buscarUsuarios(@RequestParam String termino) {
        try {
            List<UsuarioDetalleDTO> usuariosList = usuarioServicio.buscarUsuarios(termino);
            ListaUsuariosResponseDTO responseData = new ListaUsuariosResponseDTO(
                usuariosList,
                usuariosList.size()
            );
            ApiResponse response = new ApiResponse(
                true, 
                "Búsqueda completada",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al buscar usuarios: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Obtener detalles de usuario (Admin)
     * GET /api/usuarios/admin/detalles/{id}
     */
    @GetMapping("/admin/detalles/{id}")
    public ResponseEntity<ApiResponse> obtenerDetallesUsuario(@PathVariable String id) {
        try {
            UsuarioDetalleDTO usuarioDetalle = usuarioServicio.obtenerDetallesUsuario(id);
            ApiResponse response = new ApiResponse(true, "Detalles del usuario obtenidos", usuarioDetalle);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener detalles: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Actualizar usuario (Admin)
     * PUT /api/usuarios/admin/{id}
     */
    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse> actualizarUsuarioAdmin(
            @PathVariable String id,
            @Valid @RequestBody ActualizarUsuarioAdminDTO usuarioDTO) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuarioAdmin(id, usuarioDTO);
            ApiResponse response = new ApiResponse(true, "Usuario actualizado exitosamente", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al actualizar usuario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Desactivar usuario (Admin)
     * PUT /api/usuarios/admin/{id}/desactivar
     */
    @PutMapping("/admin/{id}/desactivar")
    public ResponseEntity<ApiResponse> desactivarUsuario(@PathVariable String id) {
        try {
            usuarioServicio.desactivarUsuario(id);
            ApiResponse response = new ApiResponse(true, "Usuario desactivado exitosamente", null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al desactivar usuario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Activar usuario (Admin)
     * PUT /api/usuarios/admin/{id}/activar
     */
    @PutMapping("/admin/{id}/activar")
    public ResponseEntity<ApiResponse> activarUsuario(@PathVariable String id) {
        try {
            usuarioServicio.activarUsuario(id);
            ApiResponse response = new ApiResponse(true, "Usuario activado exitosamente", null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al activar usuario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    // ============ Endpoints Legados (Compatibilidad) ============
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable String id) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}