package com.farmacia.repository;

import com.farmacia.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {
    List<Pedido> findByUserId(String userId);
    
    // Método adicional para obtener todos los pedidos
    List<Pedido> findAll();
}