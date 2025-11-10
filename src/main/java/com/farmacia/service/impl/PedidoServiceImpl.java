package com.farmacia.service.impl;

import com.farmacia.dto.CheckoutDataDTO;
import com.farmacia.dto.CheckoutItemDTO;
import com.farmacia.model.*;
import com.farmacia.repository.PedidoRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Pedido crearPedido(String userId, CheckoutDataDTO checkoutData) {
        Pedido pedido = new Pedido(userId);

        // Convert checkout items to order items
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CheckoutItemDTO item : checkoutData.getItems()) {
            Producto producto = productoRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductId()));

            OrderItem orderItem = new OrderItem(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getImagen(),
                    producto.getPrecio(),
                    item.getQuantity()
            );
            orderItems.add(orderItem);
            subtotal = subtotal.add(orderItem.getTotal());
            
            // Actualizar stock del producto
            producto.setStock(producto.getStock() - item.getQuantity());
            productoRepository.save(producto);
        }

        // Calculate totals
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.16")); // 16% IVA
        BigDecimal shipping = subtotal.compareTo(new BigDecimal("50")) >= 0 ?
                BigDecimal.ZERO : new BigDecimal("5.99");
        BigDecimal total = subtotal.add(tax).add(shipping);

        // Set pedido details
        pedido.setItems(orderItems);
        pedido.setSubtotal(subtotal);
        pedido.setTax(tax);
        pedido.setShipping(shipping);
        pedido.setTotal(total);
        pedido.setPaymentMethod(checkoutData.getPaymentMethod());

        // Set addresses directly
        pedido.setShippingAddress(checkoutData.getShippingAddress());
        pedido.setBillingAddress(checkoutData.getBillingAddress());

        pedido.setStatus("pending");
        pedido.setEstimatedDelivery(LocalDateTime.now().plusDays(3));

        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerPedidosPorUsuario(String userId) {
        return pedidoRepository.findByUserId(userId);
    }

    @Override
    public Pedido obtenerPedidoPorId(String orderId) {
        return pedidoRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + orderId));
    }
    
    // Implementación del método adicional necesario para AdminController
    @Override
    public List<Pedido> obtenerTodosPedidos() {
        return pedidoRepository.findAll();
    }
}