package com.farmacia.repository;

import com.farmacia.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {

    @Query("{'nombre': {$regex: ?0, $options: 'i'}, 'activo': true}")
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("{'categoria.$id': ObjectId(?0), 'activo': true}")
    List<Producto> findByCategoriaId(String categoriaId);

    List<Producto> findByActivoTrue();

    List<Producto> findByEnOfertaTrueAndActivoTrue();

    @Query("{'principioActivo': {$regex: ?0, $options: 'i'}, 'activo': true}")
    List<Producto> findByPrincipioActivoContainingIgnoreCase(String principioActivo);

    @Query("{'laboratorio': {$regex: ?0, $options: 'i'}, 'activo': true}")
    List<Producto> findByLaboratorioContainingIgnoreCase(String laboratorio);
}