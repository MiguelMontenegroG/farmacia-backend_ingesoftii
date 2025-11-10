package com.farmacia.service;

import com.farmacia.model.Venta;
import com.farmacia.model.ItemVenta;
import com.farmacia.model.Usuario;
import com.farmacia.repository.VentaRepository;
import com.farmacia.repository.MetricasVentasRepository;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.impl.VentaServiceImpl;
import com.farmacia.dto.ReporteVentasDTO;
import com.farmacia.dto.MetricasVentasDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private MetricasVentasRepository metricasRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private VentaServiceImpl ventaService;

    private Venta venta1;
    private Venta venta2;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("user1");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");

        ItemVenta item1 = new ItemVenta();
        item1.setNombreProducto("Producto 1");
        item1.setCantidad(2);
        item1.setPrecioUnitario(BigDecimal.valueOf(50.0));

        ItemVenta item2 = new ItemVenta();
        item2.setNombreProducto("Producto 2");
        item2.setCantidad(1);
        item2.setPrecioUnitario(BigDecimal.valueOf(30.0));

        venta1 = new Venta();
        venta1.setId("venta1");
        venta1.setUsuarioId("user1");
        venta1.setNumeroVenta("V001");
        venta1.setItems(Arrays.asList(item1, item2));
        venta1.setSubtotal(130.0f);
        venta1.setTotal(130.0f);
        venta1.setEstado("COMPLETADA");
        venta1.setFechaVenta(LocalDateTime.now().minusDays(1));

        venta2 = new Venta();
        venta2.setId("venta2");
        venta2.setUsuarioId("user1");
        venta2.setNumeroVenta("V002");
        venta2.setItems(Arrays.asList(item1));
        venta2.setSubtotal(100.0f);
        venta2.setTotal(100.0f);
        venta2.setEstado("COMPLETADA");
        venta2.setFechaVenta(LocalDateTime.now());
    }

    @Test
    void testObtenerTodasLasVentas() {
        // Given
        when(ventaRepository.findAll()).thenReturn(Arrays.asList(venta1, venta2));

        // When
        List<Venta> result = ventaService.obtenerTodasLasVentas();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(venta1, venta2);
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerVentaPorId() {
        // Given
        when(ventaRepository.findById("venta1")).thenReturn(Optional.of(venta1));

        // When
        Venta result = ventaService.obtenerVentaPorId("venta1");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("venta1");
        verify(ventaRepository, times(1)).findById("venta1");
    }

    @Test
    void testGenerarReporteVentas() {
        // Given
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fechaFin = LocalDateTime.now();
        List<Venta> ventas = Arrays.asList(venta1, venta2);

        when(ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin)).thenReturn(ventas);
        when(usuarioRepository.findById("user1")).thenReturn(Optional.of(usuario));

        // When
        ReporteVentasDTO result = ventaService.generarReporteVentas(fechaInicio, fechaFin);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalVentas()).isEqualTo(2);
        assertThat(result.getTotalIngresos()).isEqualTo(BigDecimal.valueOf(230.0)); // 130 + 100
        assertThat(result.getProductosMasVendidos()).hasSize(2);
        verify(ventaRepository, times(1)).findByFechaVentaBetween(fechaInicio, fechaFin);
    }

    @Test
    void testObtenerMetricasVentas() {
        // Given
        when(ventaRepository.findAll()).thenReturn(Arrays.asList(venta1, venta2));

        // When
        MetricasVentasDTO result = ventaService.obtenerMetricasVentas();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalVentas()).isEqualTo(2);
        assertThat(result.getTotalIngresos()).isEqualTo(BigDecimal.valueOf(230.0));
        assertThat(result.getVentasCompletadas()).isEqualTo(2);
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void testGuardarMetrica() {
        // Given
        MetricasVentasDTO metrica = new MetricasVentasDTO();
        metrica.setTotalVentas(100);

        // When
        ventaService.guardarMetrica(metrica);

        // Then
        verify(metricasRepository, times(1)).save(metrica);
    }

    @Test
    void testGuardarVenta() {
        // Given
        when(ventaRepository.save(venta1)).thenReturn(venta1);

        // When
        Venta result = ventaService.guardarVenta(venta1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("venta1");
        verify(ventaRepository, times(1)).save(venta1);
    }
}