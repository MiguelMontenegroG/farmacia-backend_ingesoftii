package com.farmacia.service;

import com.farmacia.dto.PagoRequest;
import com.farmacia.dto.PagoResponse;
import com.farmacia.model.Pago;
import java.util.List;

public interface PagoService {
    PagoResponse procesarPago(PagoRequest pagoRequest);
    Pago obtenerPagoPorId(String id);
    List<Pago> obtenerTodosLosPagos();
    List<Pago> obtenerPagosPorEstado(String estado);
    PagoResponse confirmarPago(String pagoId);
    PagoResponse cancelarPago(String pagoId);
}
