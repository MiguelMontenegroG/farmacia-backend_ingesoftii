package com.farmacia.service.impl;

import com.farmacia.model.Favorito;
import com.farmacia.repository.FavoritoRepository;
import com.farmacia.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepo;

    @Override
    public Favorito obtenerFavoritosPorUsuario(String usuarioId) {
        return favoritoRepo.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Favorito nuevo = new Favorito(usuarioId);
                    nuevo.setProductosIds(new ArrayList<>());
                    return favoritoRepo.save(nuevo);
                });
    }

    @Override
    public Favorito agregarProductoAFavoritos(String usuarioId, String productoId) {
        Favorito favorito = obtenerFavoritosPorUsuario(usuarioId);

        if (!favorito.getProductosIds().contains(productoId)) {
            favorito.getProductosIds().add(productoId);
            favorito.setFechaActualizacion(LocalDateTime.now());
            favoritoRepo.save(favorito);
        }

        return favorito;
    }

    @Override
    public Favorito eliminarProductoDeFavoritos(String usuarioId, String productoId) {
        Favorito favorito = obtenerFavoritosPorUsuario(usuarioId);
        favorito.getProductosIds().remove(productoId);
        favorito.setFechaActualizacion(LocalDateTime.now());
        return favoritoRepo.save(favorito);
    }

    @Override
    public List<String> listarIdsFavoritos(String usuarioId) {
        return obtenerFavoritosPorUsuario(usuarioId).getProductosIds();
    }

    @Override
    public boolean esFavorito(String usuarioId, String productoId) {
        return obtenerFavoritosPorUsuario(usuarioId)
                .getProductosIds()
                .contains(productoId);
    }

    @Override
    public void eliminarTodosFavoritos(String usuarioId) {
        favoritoRepo.deleteByUsuarioId(usuarioId);
    }
}


