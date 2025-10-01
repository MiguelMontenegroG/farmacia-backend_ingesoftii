package com.farmacia.repository;

import com.farmacia.model.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {
    List<Pago> findByEstado(String estado);
    List<Pago> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Pago> findByMetodoPago(String metodoPago);
    List<Pago> findByClienteEmail(String clienteEmail);
}
