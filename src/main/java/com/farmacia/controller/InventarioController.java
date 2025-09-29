package com.farmacia.controller;

import com.farmacia.service.InventarioService;
import com.farmacia.dto.InventarioDTO;
import com.farmacia.dto.ActualizarStockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> obtenerTodoElInventario() {
        List<InventarioDTO> inventario = inventarioService.obtenerTodoElInventario();
        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtenerProductoPorId(@PathVariable String id) {
        InventarioDTO producto = inventarioService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<InventarioDTO>> buscarProductosPorNombre(@RequestParam String nombre) {
        List<InventarioDTO> productos = inventarioService.buscarProductosPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }


    @PostMapping("/{id}/aumentar-stock")
    public ResponseEntity<InventarioDTO> aumentarStock(@PathVariable String id,
                                                       @RequestBody ActualizarStockRequest request) {
        InventarioDTO productoActualizado = inventarioService.aumentarStock(id, request);
        return ResponseEntity.ok(productoActualizado);
    }

    @PostMapping("/{id}/disminuir-stock")
    public ResponseEntity<InventarioDTO> disminuirStock(@PathVariable String id,
                                                        @RequestBody ActualizarStockRequest request) {
        InventarioDTO productoActualizado = inventarioService.disminuirStock(id, request);
        return ResponseEntity.ok(productoActualizado);
    }

}
