package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.model.Calendario;
import com.farmacia.service.PedidoService;
import com.farmacia.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendario")
@CrossOrigin(origins = "*")
public class CalendarioController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/proximos-envios")
    public ResponseEntity<ApiResponse<List<Calendario>>> obtenerProximosEnvios(
            @RequestParam(required = false) String userId) {
        try {
            // En una implementación completa, esto obtendría eventos del calendario
            // Por ahora, simulamos obteniendo pedidos pendientes
            List<Pedido> pedidos = List.of(); // Implementación placeholder
            
            List<Calendario> eventos = pedidos.stream()
                .filter(p -> "pending".equals(p.getStatus()))
                .map(p -> {
                    Calendario evento = new Calendario();
                    evento.setUsuarioId(p.getUserId());
                    evento.setPedidoId(p.getId());
                    evento.setTipoEvento("ENTREGA");
                    evento.setTitulo("Entrega programada para pedido #" + p.getId().substring(0, 6));
                    evento.setFechaEvento(p.getEstimatedDelivery());
                    return evento;
                })
                .collect(Collectors.toList());
                
            return ResponseEntity.ok(new ApiResponse<>(true, "Próximos envíos obtenidos exitosamente", eventos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al obtener próximos envíos: " + e.getMessage()));
        }
    }

    @PostMapping("/pedidos-recurrentes")
    public ResponseEntity<ApiResponse<Calendario>> crearPedidoRecurrente(
            @RequestBody Calendario evento) {
        try {
            // En una implementación completa, esto crearía un evento recurrente
            // Por ahora, solo devolvemos el evento recibido
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedido recurrente creado exitosamente", evento));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al crear pedido recurrente: " + e.getMessage()));
        }
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<ApiResponse<List<Calendario>>> obtenerNotificaciones(
            @RequestParam(required = false) String userId) {
        try {
            // En una implementación completa, esto obtendría notificaciones programadas
            // Por ahora, simulamos obteniendo eventos próximos
            List<Calendario> notificaciones = List.of(); // Implementación placeholder
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificaciones obtenidas exitosamente", notificaciones));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al obtener notificaciones: " + e.getMessage()));
        }
    }
}