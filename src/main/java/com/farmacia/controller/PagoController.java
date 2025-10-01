package com.farmacia.controller;

import com.farmacia.service.PagoService;
import com.farmacia.dto.PagoRequest;
import com.farmacia.dto.PagoResponse;
import com.farmacia.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<PagoResponse> procesarPago(@RequestBody PagoRequest pagoRequest) {
        PagoResponse response = pagoService.procesarPago(pagoRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable String id) {
        Pago pago = pagoService.obtenerPagoPorId(id);
        return ResponseEntity.ok(pago);
    }

    @GetMapping
    public ResponseEntity<List<Pago>> obtenerTodosLosPagos() {
        List<Pago> pagos = pagoService.obtenerTodosLosPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> obtenerPagosPorEstado(@PathVariable String estado) {
        List<Pago> pagos = pagoService.obtenerPagosPorEstado(estado);
        return ResponseEntity.ok(pagos);
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<PagoResponse> confirmarPago(@PathVariable String id) {
        PagoResponse response = pagoService.confirmarPago(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PagoResponse> cancelarPago(@PathVariable String id) {
        PagoResponse response = pagoService.cancelarPago(id);
        return ResponseEntity.ok(response);
    }
}
