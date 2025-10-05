package com.farmacia.service.impl;

import com.farmacia.dto.*;
import com.farmacia.model.Carrito;
import com.farmacia.model.Producto;
import com.farmacia.repository.CarritoRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Carrito obtenerCarritoPorUsuario(String usuarioId) {
        return carritoRepository.findByUsuarioIdAndActivoTrue(usuarioId)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito(usuarioId);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    @Override
    public Carrito agregarItem(String usuarioId, AgregarItemDTO dto) {
        validarUsuarioId(usuarioId);

        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProductoId()));

        validarProducto(producto);
        validarStock(producto, dto.getCantidad());

        carrito.agregarItem(producto, dto.getCantidad());
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito actualizarCantidad(String usuarioId, ActualizarCantidadDTO dto) {
        validarUsuarioId(usuarioId);

        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        if (dto.getCantidad() > 0) {
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProductoId()));

            validarStock(producto, dto.getCantidad());
        }

        carrito.actualizarCantidadItem(dto.getProductoId(), dto.getCantidad());
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito eliminarItem(String usuarioId, EliminarItemDTO dto) {
        validarUsuarioId(usuarioId);

        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        carrito.eliminarItem(dto.getProductoId());
        return carritoRepository.save(carrito);
    }

    @Override
    public void limpiarCarrito(String usuarioId) {
        validarUsuarioId(usuarioId);

        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        carrito.limpiar();
        carritoRepository.save(carrito);
    }

    @Override
    public void eliminarCarrito(String usuarioId) {
        validarUsuarioId(usuarioId);
        carritoRepository.deleteByUsuarioId(usuarioId);
    }

    @Override
    public CarritoResumenDTO obtenerResumen(String usuarioId) {
        validarUsuarioId(usuarioId);

        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        return new CarritoResumenDTO(
                carrito.getCantidadTotalItems(),
                carrito.getSubtotal(),
                carrito.getTotal(),
                !carrito.getItems().isEmpty()
        );
    }

    @Override
    public boolean verificarDisponibilidad(String usuarioId) {
        validarUsuarioId(usuarioId);

        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        return carrito.getItems().stream().allMatch(item -> {
            Producto producto = productoRepository.findById(item.getProductoId()).orElse(null);
            return producto != null && producto.isActivo() && producto.getStock() >= item.getCantidad();
        });
    }

    private void validarUsuarioId(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new RuntimeException("ID de usuario inválido");
        }
    }

    private void validarProducto(Producto producto) {
        if (!producto.isActivo()) {
            throw new RuntimeException("El producto '" + producto.getNombre() + "' no está disponible");
        }
    }

    private void validarStock(Producto producto, int cantidadSolicitada) {
        if (producto.getStock() < cantidadSolicitada) {
            throw new RuntimeException(
                    "Stock insuficiente para '" + producto.getNombre() +
                            "'. Disponible: " + producto.getStock() +
                            ", Solicitado: " + cantidadSolicitada
            );
        }
    }
}
