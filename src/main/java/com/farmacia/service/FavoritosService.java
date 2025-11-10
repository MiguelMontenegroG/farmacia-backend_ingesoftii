package com.farmacia.service;

import com.farmacia.model.Favorito;

import java.util.List;

public interface FavoritosService {

    List<Favorito> obtenerFavoritosPorUsuario(String usuarioId);
    boolean esFavorito(String usuarioId, String productoId);
    void agregarAFavoritos(String usuarioId, String productoId);
    void eliminarDeFavoritos(String usuarioId, String productoId);
    long contarFavoritosPorUsuario(String usuarioId);
}