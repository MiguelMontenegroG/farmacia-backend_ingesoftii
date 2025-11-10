package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;
import com.farmacia.service.UsuarioService;
import com.farmacia.service.impl.UsuarioServiceImpl;
import com.farmacia.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController
 * 
 * Proporciona endpoints de autenticación bajo la ruta /api/auth
 * Esto es lo que el frontend espera desde Vercel
 * 
 * Endpoints:
 * - POST /api/auth/login
 * - POST /api/auth/register
 * - POST /api/auth/logout
 * - GET /api/auth/me
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://*.vercel.app"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;
    
    @Autowired(required = false)
    private JwtTokenProvider jwtTokenProvider;

    /**
     * POST /api/auth/login
     * Autentica un usuario y retorna un token JWT
     * 
     * @param loginDTO Email y contraseña
     * @return Token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            LoginRespuesta respuesta = usuarioService.iniciarSesion(loginDTO);
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

    /**
     * POST /api/auth/register
     * Registra un nuevo usuario
     * 
     * @param datosRegistro Datos para el nuevo usuario
     * @return Usuario registrado
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO datosRegistro) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(datosRegistro);
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

    /**
     * POST /api/auth/logout
     * Cierra la sesión del usuario
     * 
     * @return Mensaje de confirmación
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        try {
            usuarioService.logout();
            return ResponseEntity.ok(new ApiResponse<>(true, "Sesión cerrada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Logout procesado", null));
        }
    }

    /**
     * GET /api/auth/me
     * Obtiene el perfil del usuario autenticado
     * Requiere token JWT en header: Authorization: Bearer {token}
     * 
     * @return Datos del usuario autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> obtenerPerfilActual() {
        try {
            // Obtener el usuario del SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
                if (usuario != null) {
                    ApiResponse response = new ApiResponse(true, "Perfil obtenido exitosamente", usuario);
                    return ResponseEntity.ok(response);
                }
            }
            
            // Si no hay autenticación válida
            ApiResponse response = new ApiResponse(false, "Usuario no autenticado", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener perfil: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

/**
 * AuthController Legacy Wrapper
 * Mantiene compatibilidad con rutas antiguas en /logout
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://*.vercel.app"}, allowCredentials = "true")
class AuthControllerLegacy {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        try {
            usuarioService.logout();
            return ResponseEntity.ok(new ApiResponse<>(true, "Logout successful", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Logout processed", null));
        }
    }
}