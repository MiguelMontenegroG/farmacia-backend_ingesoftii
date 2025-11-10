package com.farmacia.service;

import com.farmacia.dto.CheckoutDataDTO;
import com.farmacia.model.Pedido;
import java.util.List;

public interface PedidoService {
    Pedido crearPedido(String userId, CheckoutDataDTO checkoutData);
    List<Pedido> obtenerPedidosPorUsuario(String userId);
    Pedido obtenerPedidoPorId(String orderId);
    
    // Método adicional necesario para AdminController
    List<Pedido> obtenerTodosPedidos();
}