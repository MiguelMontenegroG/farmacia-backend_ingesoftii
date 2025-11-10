package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.model.Oferta;
import com.farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "*")
public class OfertaController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Oferta>>> obtenerTodasLasOfertas() {
        try {
            List<Oferta> ofertas = productoService.obtenerTodasLasOfertas();
            return ResponseEntity.ok(new ApiResponse<>(true, "Ofertas obtenidas exitosamente", ofertas));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al obtener ofertas: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Oferta>> crearOferta(@RequestBody Oferta oferta) {
        try {
            Oferta nuevaOferta = productoService.crearOferta(oferta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Oferta creada exitosamente", nuevaOferta));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al crear oferta: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Oferta>> actualizarOferta(@PathVariable String id, @RequestBody Oferta oferta) {
        try {
            Oferta ofertaActualizada = productoService.actualizarOferta(id, oferta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Oferta actualizada exitosamente", ofertaActualizada));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al actualizar oferta: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarOferta(@PathVariable String id) {
        try {
            productoService.eliminarOferta(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Oferta eliminada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al eliminar oferta: " + e.getMessage()));
        }
    }
}