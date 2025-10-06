package com.farmacia.controller;

import com.farmacia.dto.*;
import com.farmacia.model.Carrito;
import com.farmacia.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:3000")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;


    @GetMapping("/obtener/{usuarioId}")
    public ResponseEntity<?> obtenerCarritoCompleto(@PathVariable String usuarioId) {
        try {
            Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
            return ResponseEntity.ok(crearRespuestaExito("Carrito obtenido exitosamente", carrito));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }


    @GetMapping("/resumen/{usuarioId}")
    public ResponseEntity<?> obtenerResumenCarrito(@PathVariable String usuarioId) {
        try {
            CarritoResumenDTO resumen = carritoService.obtenerResumen(usuarioId);
            return ResponseEntity.ok(crearRespuestaExito("Resumen obtenido exitosamente", resumen));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    @GetMapping("/verificar-disponibilidad/{usuarioId}")
    public ResponseEntity<?> verificarDisponibilidadProductos(@PathVariable String usuarioId) {
        try {
            boolean disponible = carritoService.verificarDisponibilidad(usuarioId);
            Map<String, Object> response = new HashMap<>();
            response.put("disponible", disponible);
            response.put("mensaje", disponible ?
                    "Todos los productos están disponibles" :
                    "Algunos productos no están disponibles o no tienen stock suficiente");
            return ResponseEntity.ok(crearRespuestaExito(null, response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }


    @PostMapping("/agregar-producto/{usuarioId}")
    public ResponseEntity<?> agregarProductoAlCarrito(
            @PathVariable String usuarioId,
            @Valid @RequestBody AgregarItemDTO dto) {
        try {
            Carrito carrito = carritoService.agregarItem(usuarioId, dto);
            return ResponseEntity.ok(crearRespuestaExito("Producto agregado al carrito exitosamente", carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }


    @PutMapping("/actualizar-cantidad/{usuarioId}")
    public ResponseEntity<?> actualizarCantidadProducto(
            @PathVariable String usuarioId,
            @Valid @RequestBody ActualizarCantidadDTO dto) {
        try {
            Carrito carrito = carritoService.actualizarCantidad(usuarioId, dto);
            String mensaje = dto.getCantidad() == 0 ?
                    "Producto eliminado del carrito" :
                    "Cantidad actualizada exitosamente";
            return ResponseEntity.ok(crearRespuestaExito(mensaje, carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar-producto/{usuarioId}")
    public ResponseEntity<?> eliminarProductoDelCarrito(
            @PathVariable String usuarioId,
            @Valid @RequestBody EliminarItemDTO dto) {
        try {
            Carrito carrito = carritoService.eliminarItem(usuarioId, dto);
            return ResponseEntity.ok(crearRespuestaExito("Producto eliminado del carrito exitosamente", carrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }


    @DeleteMapping("/limpiar-carrito/{usuarioId}")
    public ResponseEntity<?> limpiarCarritoCompleto(@PathVariable String usuarioId) {
        try {
            carritoService.limpiarCarrito(usuarioId);
            return ResponseEntity.ok(crearRespuestaExito("Carrito limpiado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * DELETE /api/carrito/eliminar-carrito/{usuarioId}
     * Elimina completamente el carrito del usuario de la base de datos
     */
    @DeleteMapping("/eliminar-carrito/{usuarioId}")
    public ResponseEntity<?> eliminarCarritoCompletamente(@PathVariable String usuarioId) {
        try {
            carritoService.eliminarCarrito(usuarioId);
            return ResponseEntity.ok(crearRespuestaExito("Carrito eliminado completamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }


    private Map<String, Object> crearRespuestaExito(String mensaje, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        if (mensaje != null) {
            response.put("mensaje", mensaje);
        }
        if (data != null) {
            response.put("data", data);
        }
        return response;
    }

    private Map<String, Object> crearRespuestaError(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", mensaje);
        return response;
    }
}