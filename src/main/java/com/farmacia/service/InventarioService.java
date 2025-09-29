package com.farmacia.service;

import com.farmacia.dto.InventarioDTO;
import com.farmacia.dto.ActualizarStockRequest;
import java.util.List;

public interface InventarioService {

    List<InventarioDTO> obtenerTodoElInventario();

    InventarioDTO obtenerProductoPorId(String id);

    List<InventarioDTO> buscarProductosPorNombre(String nombre);

    InventarioDTO aumentarStock(String id, ActualizarStockRequest request);

    InventarioDTO disminuirStock(String id, ActualizarStockRequest request);
}