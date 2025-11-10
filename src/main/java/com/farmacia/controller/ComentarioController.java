package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.dto.CalificacionPromedioResponseDTO;
import com.farmacia.dto.ComentarioDTO;
import com.farmacia.dto.ComentariosProductoResponseDTO;
import com.farmacia.dto.ComentariosSpamResponseDTO;
import com.farmacia.dto.ReportarSpamResponseDTO;
import com.farmacia.model.Comentario;
import com.farmacia.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    /**
     * Crear un nuevo comentario para un producto
     * POST /api/comentarios
     */
    @PostMapping
    public ResponseEntity<ApiResponse> crearComentario(@Valid @RequestBody ComentarioDTO comentarioDTO) {
        try {
            Comentario comentario = comentarioService.crearComentario(comentarioDTO);
            ApiResponse response = new ApiResponse(true, "Comentario creado exitosamente", comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al crear comentario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Obtener todos los comentarios de un producto
     * GET /api/comentarios/producto/{productoId}
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<ApiResponse> obtenerComentariosProducto(@PathVariable String productoId) {
        try {
            List<ComentarioDTO> comentariosList = comentarioService.obtenerComentariosProducto(productoId);
            Double calificacionPromedioValue = comentarioService.obtenerCalificacionPromedio(productoId);
            
            ComentariosProductoResponseDTO responseData = new ComentariosProductoResponseDTO(
                comentariosList,
                calificacionPromedioValue,
                comentariosList.size()
            );
            
            ApiResponse response = new ApiResponse(
                true, 
                "Comentarios obtenidos exitosamente",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener comentarios: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Obtener todos los comentarios de un usuario
     * GET /api/comentarios/usuario/{usuarioId}
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse> obtenerComentariosUsuario(@PathVariable String usuarioId) {
        try {
            List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosUsuario(usuarioId);
            ApiResponse response = new ApiResponse(true, "Comentarios obtenidos exitosamente", comentarios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener comentarios: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Obtener un comentario específico
     * GET /api/comentarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> obtenerComentario(@PathVariable String id) {
        try {
            var comentario = comentarioService.obtenerComentario(id);
            if (comentario.isPresent()) {
                ApiResponse response = new ApiResponse(true, "Comentario obtenido exitosamente", comentario.get());
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(false, "Comentario no encontrado", null);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener comentario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Actualizar un comentario existente
     * PUT /api/comentarios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> actualizarComentario(
            @PathVariable String id,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {
        try {
            Comentario comentarioActualizado = comentarioService.actualizarComentario(id, comentarioDTO);
            ApiResponse response = new ApiResponse(true, "Comentario actualizado exitosamente", comentarioActualizado);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al actualizar comentario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Eliminar (desactivar) un comentario
     * DELETE /api/comentarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarComentario(@PathVariable String id) {
        try {
            comentarioService.eliminarComentario(id);
            ApiResponse response = new ApiResponse(true, "Comentario eliminado exitosamente", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al eliminar comentario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Reportar un comentario como spam
     * POST /api/comentarios/{id}/reportar-spam
     */
    @PostMapping("/{id}/reportar-spam")
    public ResponseEntity<ApiResponse> reportarSpam(@PathVariable String id) {
        try {
            Comentario comentario = comentarioService.reportarSpam(id);
            ReportarSpamResponseDTO responseData = new ReportarSpamResponseDTO(
                comentario.getId(),
                comentario.getReportesSpam(),
                comentario.isFlaggedAsSpam()
            );
            
            ApiResponse response = new ApiResponse(
                true, 
                "Comentario reportado como spam",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al reportar spam: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Obtener comentarios flagged como spam (para moderadores)
     * GET /api/comentarios/moderacion/spam
     */
    @GetMapping("/moderacion/spam")
    public ResponseEntity<ApiResponse> obtenerComentariosSpam() {
        try {
            List<ComentarioDTO> comentariosList = comentarioService.obtenerComentariosSpam();
            ComentariosSpamResponseDTO responseData = new ComentariosSpamResponseDTO(
                comentariosList,
                comentariosList.size()
            );
            
            ApiResponse response = new ApiResponse(
                true, 
                "Comentarios spam obtenidos",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener comentarios spam: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Aceptar un comentario (aprobar para mostrar)
     * POST /api/comentarios/{id}/aceptar
     */
    @PostMapping("/{id}/aceptar")
    public ResponseEntity<ApiResponse> aceptarComentario(@PathVariable String id) {
        try {
            comentarioService.aceptarComentario(id);
            ApiResponse response = new ApiResponse(true, "Comentario aceptado", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al aceptar comentario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Rechazar un comentario (no mostrar)
     * POST /api/comentarios/{id}/rechazar
     */
    @PostMapping("/{id}/rechazar")
    public ResponseEntity<ApiResponse> rechazarComentario(@PathVariable String id) {
        try {
            comentarioService.rechazarComentario(id);
            ApiResponse response = new ApiResponse(true, "Comentario rechazado", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al rechazar comentario: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Obtener calificación promedio de un producto
     * GET /api/comentarios/producto/{productoId}/calificacion-promedio
     */
    @GetMapping("/producto/{productoId}/calificacion-promedio")
    public ResponseEntity<ApiResponse> obtenerCalificacionPromedio(@PathVariable String productoId) {
        try {
            Double promedio = comentarioService.obtenerCalificacionPromedio(productoId);
            CalificacionPromedioResponseDTO responseData = new CalificacionPromedioResponseDTO(
                productoId,
                promedio
            );
            
            ApiResponse response = new ApiResponse(
                true, 
                "Calificación promedio obtenida",
                responseData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error al obtener calificación promedio: " + e.getMessage(), null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}