package com.farmacia.service;

import com.farmacia.model.Notificacion;

import java.util.List;

public interface NotificacionesService {

    List<Notificacion> obtenerNotificacionesPorUsuario(String usuarioId);
    long contarNotificacionesNoLeidas(String usuarioId);
    void marcarComoLeida(String notificacionId, String usuarioId);
    void marcarTodasComoLeidas(String usuarioId);
    void eliminarNotificacion(String notificacionId, String usuarioId);
    void crearNotificacion(Notificacion notificacion);
}