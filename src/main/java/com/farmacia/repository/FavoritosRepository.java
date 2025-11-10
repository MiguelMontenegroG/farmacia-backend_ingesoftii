package com.farmacia.repository;

import com.farmacia.model.Favorito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritosRepository extends MongoRepository<Favorito, String> {

    List<Favorito> findByUsuarioIdOrderByFechaAgregadoDesc(String usuarioId);
    Optional<Favorito> findByUsuarioIdAndProductoId(String usuarioId, String productoId);
    long countByUsuarioId(String usuarioId);
}