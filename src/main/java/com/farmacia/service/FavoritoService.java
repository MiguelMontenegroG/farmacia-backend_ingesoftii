package com.farmacia.service;

import com.farmacia.model.Favorito;
import java.util.List;

public interface FavoritoService {

    Favorito agregarFavorito(String usuarioId, String productoId);

    void eliminarFavorito(String usuarioId, String productoId);

    List<Favorito> listarFavoritos(String usuarioId);

    boolean esFavorito(String usuarioId, String productoId);

    void eliminarTodosPorUsuario(String usuarioId);
}
