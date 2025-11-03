package com.farmacia.service;

import com.farmacia.model.Favorito;
import java.util.List;

public interface FavoritoService {

    Favorito obtenerFavoritosPorUsuario(String usuarioId);
    Favorito agregarProductoAFavoritos(String usuarioId, String productoId);
    Favorito eliminarProductoDeFavoritos(String usuarioId, String productoId);
    List<String> listarIdsFavoritos(String usuarioId);
    boolean esFavorito(String usuarioId, String productoId);
    void eliminarTodosFavoritos(String usuarioId);

}
