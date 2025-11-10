package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.dto.CheckoutDataDTO;
import com.farmacia.model.Pedido;
import com.farmacia.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<Pedido>> crearPedido(@RequestBody CheckoutDataDTO checkoutData) {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String userId = "usuario-prueba-id";
            Pedido pedido = pedidoService.crearPedido(userId, checkoutData);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedido creado exitosamente", pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error al crear el pedido: " + e.getMessage()));
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse<List<Pedido>>> obtenerPedidosUsuario(@PathVariable String usuarioId) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedidos obtenidos exitosamente", pedidos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error al obtener pedidos: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Pedido>> obtenerPedido(@PathVariable String orderId) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(orderId);
            if (pedido != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Pedido obtenido exitosamente", pedido));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error al obtener pedido: " + e.getMessage()));
        }
    }
}