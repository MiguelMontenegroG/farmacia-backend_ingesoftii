package com.farmacia.repository;

import com.farmacia.model.Carrito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarritoRepository extends MongoRepository<Carrito, String> {
    Optional<Carrito> findByUsuarioIdAndActivoTrue(String usuarioId);
    void deleteByUsuarioId(String usuarioId);
    boolean existsByUsuarioIdAndActivoTrue(String usuarioId);
}