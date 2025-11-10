package com.farmacia.repository;

import com.farmacia.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionesRepository extends MongoRepository<Notificacion, String> {

    List<Notificacion> findByUsuarioIdOrderByFechaCreacionDesc(String usuarioId);
    List<Notificacion> findByUsuarioIdAndLeidaFalse(String usuarioId);
    long countByUsuarioIdAndLeidaFalse(String usuarioId);
    Notificacion findByIdAndUsuarioId(String id, String usuarioId);
}