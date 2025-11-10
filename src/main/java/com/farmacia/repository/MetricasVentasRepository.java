package com.farmacia.repository;

import com.farmacia.dto.MetricasVentasDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetricasVentasRepository extends MongoRepository<MetricasVentasDTO, String> {

    List<MetricasVentasDTO> findByFechaCalculoAfterOrderByFechaCalculoDesc(LocalDateTime fecha);

    MetricasVentasDTO findFirstByOrderByFechaCalculoDesc();
}