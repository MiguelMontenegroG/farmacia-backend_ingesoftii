package com.farmacia.repository;

import com.farmacia.model.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String> {

    @Query("{'nombre': {$regex: ?0, $options: 'i'}, 'activo': true}")
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

    List<Categoria> findByActivoTrue();

    List<Categoria> findByCategoriaPadreIsNullAndActivoTrue();

    @Query("{'categoriaPadre.$id': ?0, 'activo': true}")
    List<Categoria> findByCategoriaPadreId(String categoriaPadreId);

    Optional<Categoria> findByNombreAndActivoTrue(String nombre);

    @Query("{'keywords': {$in: [?0]}, 'activo': true}")
    List<Categoria> findByKeyword(String keyword);
}