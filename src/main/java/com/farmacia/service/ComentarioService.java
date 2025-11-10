package com.farmacia.service;

import com.farmacia.dto.ComentarioDTO;
import com.farmacia.model.Comentario;
import java.util.List;
import java.util.Optional;

public interface ComentarioService {
    
    // Crear un nuevo comentario con validación
    Comentario crearComentario(ComentarioDTO comentarioDTO) throws IllegalArgumentException;
    
    // Obtener comentarios de un producto (solo activos)
    List<ComentarioDTO> obtenerComentariosProducto(String productoId);
    
    // Obtener todos los comentarios de un usuario
    List<ComentarioDTO> obtenerComentariosUsuario(String usuarioId);
    
    // Obtener un comentario específico
    Optional<ComentarioDTO> obtenerComentario(String id);
    
    // Actualizar un comentario
    Comentario actualizarComentario(String id, ComentarioDTO comentarioDTO) throws IllegalArgumentException;
    
    // Eliminar (desactivar) un comentario
    void eliminarComentario(String id);
    
    // Reportar comentario como spam
    Comentario reportarSpam(String id) throws IllegalArgumentException;
    
    // Obtener comentarios flagged como spam
    List<ComentarioDTO> obtenerComentariosSpam();
    
    // Aprobar/rechazar comentario (moderación)
    void aceptarComentario(String id) throws IllegalArgumentException;
    void rechazarComentario(String id) throws IllegalArgumentException;
    
    // Obtener calificación promedio de un producto
    Double obtenerCalificacionPromedio(String productoId);
}