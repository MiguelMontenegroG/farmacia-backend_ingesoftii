package com.farmacia.service;

import com.farmacia.dto.FiltroProductoDTO;
import com.farmacia.model.Oferta;
import com.farmacia.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> obtenerCatalogo();

    List<Producto> buscarProductosPorNombre(String nombre);

    List<Producto> buscarProductosPorCategoria(String categoriaId);

    List<Producto> buscarPorPrincipioActivo(String principioActivo);

    List<Producto> buscarPorLaboratorio(String laboratorio);

    Optional<Producto> obtenerProductoPorId(String id);

    List<Producto> obtenerProductosEnOferta();

    Producto guardarProducto(Producto producto);

    void desactivarProducto(String id);

    Producto obtenerPorId(String id);

    List<Producto> filtrarProductos(FiltroProductoDTO filtros);
    
    // Métodos para gestión de ofertas
    List<Oferta> obtenerTodasLasOfertas();
    Oferta crearOferta(Oferta oferta);
    Oferta actualizarOferta(String id, Oferta oferta);
    void eliminarOferta(String id);
}