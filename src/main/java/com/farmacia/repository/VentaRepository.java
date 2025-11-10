package com.farmacia.repository;

import com.farmacia.model.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends MongoRepository<Venta, String> {

    List<Venta> findByUsuarioId(String usuarioId);

    List<Venta> findByEstado(String estado);

    List<Venta> findByVendedorId(String vendedorId);

    @Query("{ 'fechaVenta' : { $gte: ?0, $lte: ?1 } }")
    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("{ 'fechaVenta' : { $gte: ?0 } }")
    List<Venta> findByFechaVentaAfter(LocalDateTime fecha);

    long countByEstado(String estado);

    @Query(value = "{ 'fechaVenta' : { $gte: ?0, $lte: ?1 } }", count = true)
    long countByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}