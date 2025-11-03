package com.farmacia.repository;


import com.farmacia.model.Favorito;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends MongoRepository<Favorito, String> {
    List<Favorito> findByUsuarioId(String usuarioId);
    Optional<Favorito> findByUsuarioIdAndProducto_Id(String usuarioId, String productoId);
    void deleteByUsuarioIdAndProducto_Id(String usuarioId, String productoId);
}
