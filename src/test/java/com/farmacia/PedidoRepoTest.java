package com.farmacia;

import com.farmacia.model.OrderItem;
import com.farmacia.model.Pedido;
import com.farmacia.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class PedidoRepoTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void testCrearPedidosYBuscarPorUsuario() {
        // Arrange - Crear pedidos para diferentes usuarios
        Pedido pedido1 = new Pedido("user123");
        pedido1.setSubtotal(new BigDecimal("50000"));
        pedido1.setTax(new BigDecimal("9500"));
        pedido1.setShipping(new BigDecimal("5000"));
        pedido1.setTotal(new BigDecimal("64500"));
        pedido1.setStatus("pending");
        pedido1.setPaymentMethod("tarjeta");

        OrderItem item1 = new OrderItem("prod1", "Paracetamol", "img1.jpg", new BigDecimal("2500"), 2);
        pedido1.setItems(List.of(item1));

        Pedido pedido2 = new Pedido("user123"); // Mismo usuario
        pedido2.setSubtotal(new BigDecimal("30000"));
        pedido2.setTax(new BigDecimal("5700"));
        pedido2.setShipping(new BigDecimal("0"));
        pedido2.setTotal(new BigDecimal("35700"));
        pedido2.setStatus("processing");

        Pedido pedido3 = new Pedido("user456"); // Otro usuario
        pedido3.setSubtotal(new BigDecimal("15000"));
        pedido3.setTax(new BigDecimal("2850"));
        pedido3.setShipping(new BigDecimal("3000"));
        pedido3.setTotal(new BigDecimal("20850"));
        pedido3.setStatus("shipped");

        // Act - Guardar pedidos
        pedidoRepository.saveAll(List.of(pedido1, pedido2, pedido3));

        // Assert - Verificar búsqueda por userId
        List<Pedido> pedidosUser123 = pedidoRepository.findByUserId("user123");
        assertThat(pedidosUser123).hasSize(2);
        assertThat(pedidosUser123).extracting(Pedido::getStatus)
                .containsExactlyInAnyOrder("pending", "processing");

        List<Pedido> pedidosUser456 = pedidoRepository.findByUserId("user456");
        assertThat(pedidosUser456).hasSize(1);
        assertThat(pedidosUser456.get(0).getStatus()).isEqualTo("shipped");

        List<Pedido> pedidosUserInexistente = pedidoRepository.findByUserId("nonexistent");
        assertThat(pedidosUserInexistente).isEmpty();
    }
}