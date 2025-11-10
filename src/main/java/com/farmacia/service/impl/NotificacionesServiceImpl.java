package com.farmacia.service.impl;

import com.farmacia.model.Notificacion;
import com.farmacia.repository.NotificacionesRepository;
import com.farmacia.service.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionesServiceImpl implements NotificacionesService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    @Override
    public List<Notificacion> obtenerNotificacionesPorUsuario(String usuarioId) {
        return notificacionesRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    @Override
    public long contarNotificacionesNoLeidas(String usuarioId) {
        return notificacionesRepository.countByUsuarioIdAndLeidaFalse(usuarioId);
    }

    @Override
    public void marcarComoLeida(String notificacionId, String usuarioId) {
        Notificacion notificacion = notificacionesRepository.findByIdAndUsuarioId(notificacionId, usuarioId);
        if (notificacion != null) {
            notificacion.setLeida(true);
            notificacionesRepository.save(notificacion);
        }
    }

    @Override
    public void marcarTodasComoLeidas(String usuarioId) {
        List<Notificacion> notificacionesNoLeidas = notificacionesRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
        notificacionesNoLeidas.forEach(notif -> notif.setLeida(true));
        notificacionesRepository.saveAll(notificacionesNoLeidas);
    }

    @Override
    public void eliminarNotificacion(String notificacionId, String usuarioId) {
        Notificacion notificacion = notificacionesRepository.findByIdAndUsuarioId(notificacionId, usuarioId);
        if (notificacion != null) {
            notificacionesRepository.delete(notificacion);
        }
    }

    @Override
    public void crearNotificacion(Notificacion notificacion) {
        notificacionesRepository.save(notificacion);
    }
}