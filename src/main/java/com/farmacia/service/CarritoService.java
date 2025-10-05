package com.farmacia.service;

import com.farmacia.dto.*;
import com.farmacia.model.Carrito;

public interface CarritoService {
    Carrito obtenerCarritoPorUsuario(String usuarioId);
    Carrito agregarItem(String usuarioId, AgregarItemDTO dto);
    Carrito actualizarCantidad(String usuarioId, ActualizarCantidadDTO dto);
    Carrito eliminarItem(String usuarioId, EliminarItemDTO dto);
    void limpiarCarrito(String usuarioId);
    void eliminarCarrito(String usuarioId);
    CarritoResumenDTO obtenerResumen(String usuarioId);
    boolean verificarDisponibilidad(String usuarioId);
}