package com.farmacia.repository;

import com.farmacia.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    
    // Obtener todos los comentarios de un producto (activos)
    List<Comentario> findByProductoIdAndActivoTrue(String productoId);
    
    // Obtener todos los comentarios de un usuario
    List<Comentario> findByUsuarioId(String usuarioId);
    
    // Obtener comentarios flagged como spam
    List<Comentario> findByFlaggedAsSpamTrue();
    
    // Obtener comentarios con más de X reportes de spam
    @Query("{ 'reportesSpam': { $gte: ?0 } }")
    List<Comentario> findComentariosWithHighSpamReports(Integer threshold);
    
    // Contar comentarios de un producto (activos)
    long countByProductoIdAndActivoTrue(String productoId);
    
    // Contar comentarios de un usuario
    long countByUsuarioId(String usuarioId);
    
    // Verificar si un usuario ya comentó un producto
    Optional<Comentario> findByProductoIdAndUsuarioId(String productoId, String usuarioId);
}