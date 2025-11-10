package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.model.Notificacion;
import com.farmacia.service.NotificacionesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;

    @GetMapping
    @Operation(summary = "Obtener notificaciones del usuario")
    public ResponseEntity<ApiResponse<List<Notificacion>>> obtenerNotificaciones() {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            List<Notificacion> notificaciones = notificacionesService.obtenerNotificacionesPorUsuario(usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificaciones obtenidas exitosamente", notificaciones));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al obtener notificaciones: " + e.getMessage()));
        }
    }

    @GetMapping("/no-leidas")
    @Operation(summary = "Obtener conteo de notificaciones no leídas")
    public ResponseEntity<ApiResponse<Long>> obtenerConteoNoLeidas() {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            long conteo = notificacionesService.contarNotificacionesNoLeidas(usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Conteo obtenido exitosamente", conteo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al obtener conteo: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/marcar-leida")
    @Operation(summary = "Marcar notificación como leída")
    public ResponseEntity<ApiResponse<Void>> marcarComoLeida(@PathVariable String id) {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            notificacionesService.marcarComoLeida(id, usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificación marcada como leída", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al marcar notificación como leída: " + e.getMessage()));
        }
    }

    @PutMapping("/marcar-todas-leidas")
    @Operation(summary = "Marcar todas las notificaciones como leídas")
    public ResponseEntity<ApiResponse<Void>> marcarTodasComoLeidas() {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            notificacionesService.marcarTodasComoLeidas(usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Todas las notificaciones marcadas como leídas", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al marcar todas las notificaciones como leídas: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación")
    public ResponseEntity<ApiResponse<Void>> eliminarNotificacion(@PathVariable String id) {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            notificacionesService.eliminarNotificacion(id, usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificación eliminada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al eliminar notificación: " + e.getMessage()));
        }
    }
}