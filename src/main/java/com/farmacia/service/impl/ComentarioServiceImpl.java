package com.farmacia.service.impl;

import com.farmacia.dto.ComentarioDTO;
import com.farmacia.model.Comentario;
import com.farmacia.repository.ComentarioRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Palabras prohibidas para detección de spam
    private static final String[] PALABRAS_PROHIBIDAS = {
        "promo", "click", "link", "whatsapp", "telegram", "comprar aqui",
        "casino", "poker", "viagra", "bitcoin", "cripto"
    };
    
    // Límite de comentarios por usuario en 24 horas
    private static final int LIMITE_COMENTARIOS_24H = 5;
    
    // Límite de reportes de spam para flagging automático
    private static final int LIMITE_REPORTES_SPAM = 3;
    
    @Override
    public Comentario crearComentario(ComentarioDTO comentarioDTO) throws IllegalArgumentException {
        // Validar que el producto existe
        if (!productoRepository.existsById(comentarioDTO.getProductoId())) {
            throw new IllegalArgumentException("El producto no existe");
        }
        
        // Validar que el usuario existe
        if (!usuarioRepository.existsById(comentarioDTO.getUsuarioId())) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        
        // Validar que el usuario no haya comentado el mismo producto
        Optional<Comentario> comentarioExistente = 
            comentarioRepository.findByProductoIdAndUsuarioId(
                comentarioDTO.getProductoId(), 
                comentarioDTO.getUsuarioId()
            );
        
        if (comentarioExistente.isPresent() && comentarioExistente.get().isActivo()) {
            throw new IllegalArgumentException("Ya has comentado este producto");
        }
        
        // Validación de spam
        validarSpam(comentarioDTO);
        
        // Crear comentario
        Comentario comentario = new Comentario();
        comentario.setProductoId(comentarioDTO.getProductoId());
        comentario.setUsuarioId(comentarioDTO.getUsuarioId());
        comentario.setNombreUsuario(comentarioDTO.getNombreUsuario());
        comentario.setCalificacion(comentarioDTO.getCalificacion());
        comentario.setTitulo(comentarioDTO.getTitulo());
        comentario.setContenido(comentarioDTO.getContenido());
        comentario.setFechaCreacion(LocalDateTime.now());
        comentario.setActivo(true);
        
        return comentarioRepository.save(comentario);
    }
    
    @Override
    public List<ComentarioDTO> obtenerComentariosProducto(String productoId) {
        return comentarioRepository.findByProductoIdAndActivoTrue(productoId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<ComentarioDTO> obtenerComentariosUsuario(String usuarioId) {
        return comentarioRepository.findByUsuarioId(usuarioId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public Optional<ComentarioDTO> obtenerComentario(String id) {
        return comentarioRepository.findById(id)
            .map(this::convertToDTO);
    }
    
    @Override
    public Comentario actualizarComentario(String id, ComentarioDTO comentarioDTO) 
            throws IllegalArgumentException {
        
        Comentario comentario = comentarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        
        // Validar que solo el propietario pueda editar
        if (!comentario.getUsuarioId().equals(comentarioDTO.getUsuarioId())) {
            throw new IllegalArgumentException("No tienes permiso para editar este comentario");
        }
        
        // Validación de spam para el contenido actualizado
        validarSpam(comentarioDTO);
        
        // Actualizar campos
        if (comentarioDTO.getTitulo() != null) {
            comentario.setTitulo(comentarioDTO.getTitulo());
        }
        if (comentarioDTO.getContenido() != null) {
            comentario.setContenido(comentarioDTO.getContenido());
        }
        if (comentarioDTO.getCalificacion() != null) {
            comentario.setCalificacion(comentarioDTO.getCalificacion());
        }
        
        return comentarioRepository.save(comentario);
    }
    
    @Override
    public void eliminarComentario(String id) {
        Comentario comentario = comentarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        
        comentario.setActivo(false);
        comentarioRepository.save(comentario);
    }
    
    @Override
    public Comentario reportarSpam(String id) throws IllegalArgumentException {
        Comentario comentario = comentarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        
        // Incrementar contador de reportes
        int reportesActuales = comentario.getReportesSpam() != null ? comentario.getReportesSpam() : 0;
        comentario.setReportesSpam(reportesActuales + 1);
        
        // Si excede límite, flagear automáticamente
        if (comentario.getReportesSpam() >= LIMITE_REPORTES_SPAM) {
            comentario.setFlaggedAsSpam(true);
        }
        
        return comentarioRepository.save(comentario);
    }
    
    @Override
    public List<ComentarioDTO> obtenerComentariosSpam() {
        return comentarioRepository.findByFlaggedAsSpamTrue()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public void aceptarComentario(String id) throws IllegalArgumentException {
        Comentario comentario = comentarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        
        comentario.setFlaggedAsSpam(false);
        comentario.setActivo(true);
        comentario.setReportesSpam(0);
        comentarioRepository.save(comentario);
    }
    
    @Override
    public void rechazarComentario(String id) throws IllegalArgumentException {
        Comentario comentario = comentarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado"));
        
        comentario.setActivo(false);
        comentarioRepository.save(comentario);
    }
    
    @Override
    public Double obtenerCalificacionPromedio(String productoId) {
        List<Comentario> comentarios = comentarioRepository.findByProductoIdAndActivoTrue(productoId);
        
        if (comentarios.isEmpty()) {
            return 0.0;
        }
        
        return comentarios.stream()
            .mapToDouble(Comentario::getCalificacion)
            .average()
            .orElse(0.0);
    }
    
    // Métodos privados
    
    /**
     * Validar contenido del comentario contra spam
     */
    private void validarSpam(ComentarioDTO comentarioDTO) throws IllegalArgumentException {
        String contenidoLower = comentarioDTO.getContenido().toLowerCase();
        String tituloLower = comentarioDTO.getTitulo() != null ? comentarioDTO.getTitulo().toLowerCase() : "";
        
        // Validar palabras prohibidas
        for (String palabra : PALABRAS_PROHIBIDAS) {
            if (contenidoLower.contains(palabra) || tituloLower.contains(palabra)) {
                throw new IllegalArgumentException(
                    "El comentario contiene contenido sospechoso o palabras prohibidas"
                );
            }
        }
        
        // Validar spam de caracteres (muchas mayúsculas)
        if (esTextoEnMayusculas(comentarioDTO.getContenido())) {
            throw new IllegalArgumentException("El comentario no puede estar completamente en mayúsculas");
        }
        
        // Validar URLs
        if (comentarioDTO.getContenido().contains("http://") || comentarioDTO.getContenido().contains("https://")) {
            throw new IllegalArgumentException("No se permiten URLs en comentarios");
        }
        
        // Validar repetición excesiva de caracteres
        if (tieneRepeticionExcesiva(comentarioDTO.getContenido())) {
            throw new IllegalArgumentException("El comentario contiene caracteres repetidos excesivamente");
        }
    }
    
    /**
     * Verificar si el texto tiene muchas mayúsculas (más del 70%)
     */
    private boolean esTextoEnMayusculas(String texto) {
        if (texto.length() < 5) return false;
        
        long mayusculas = texto.chars().filter(Character::isUpperCase).count();
        double porcentaje = (double) mayusculas / texto.length();
        return porcentaje > 0.7;
    }
    
    /**
     * Verificar si hay caracteres repetidos más de 3 veces consecutivas
     */
    private boolean tieneRepeticionExcesiva(String texto) {
        for (int i = 0; i < texto.length() - 3; i++) {
            if (texto.charAt(i) == texto.charAt(i + 1) &&
                texto.charAt(i) == texto.charAt(i + 2) &&
                texto.charAt(i) == texto.charAt(i + 3)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Convertir Comentario a ComentarioDTO
     */
    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setProductoId(comentario.getProductoId());
        dto.setUsuarioId(comentario.getUsuarioId());
        dto.setNombreUsuario(comentario.getNombreUsuario());
        dto.setCalificacion(comentario.getCalificacion());
        dto.setTitulo(comentario.getTitulo());
        dto.setContenido(comentario.getContenido());
        dto.setFechaCreacion(comentario.getFechaCreacion());
        dto.setActivo(comentario.isActivo());
        dto.setFlaggedAsSpam(comentario.isFlaggedAsSpam());
        dto.setReportesSpam(comentario.getReportesSpam());
        return dto;
    }
}