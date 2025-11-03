package com.farmacia.repository;



import com.farmacia.model.Favorito;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FavoritoRepository extends MongoRepository<Favorito, String> {
    Optional<Favorito> findByUsuarioId(String usuarioId);
    void deleteByUsuarioId(String usuarioId);
}
