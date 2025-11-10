package com.farmacia.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReporteVentasDTO {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int totalVentas;
    private BigDecimal totalIngresos;
    private BigDecimal promedioVenta;
    private List<VentaResumenDTO> ventas;
    private List<ProductoMasVendidoDTO> productosMasVendidos;
    private List<VentasPorDiaDTO> ventasPorDia;

    // Constructores
    public ReporteVentasDTO() {}

    public ReporteVentasDTO(LocalDateTime fechaInicio, LocalDateTime fechaFin, int totalVentas,
                           BigDecimal totalIngresos, BigDecimal promedioVenta,
                           List<VentaResumenDTO> ventas, List<ProductoMasVendidoDTO> productosMasVendidos,
                           List<VentasPorDiaDTO> ventasPorDia) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.totalVentas = totalVentas;
        this.totalIngresos = totalIngresos;
        this.promedioVenta = promedioVenta;
        this.ventas = ventas;
        this.productosMasVendidos = productosMasVendidos;
        this.ventasPorDia = ventasPorDia;
    }

    // Getters y Setters
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public int getTotalVentas() { return totalVentas; }
    public void setTotalVentas(int totalVentas) { this.totalVentas = totalVentas; }

    public BigDecimal getTotalIngresos() { return totalIngresos; }
    public void setTotalIngresos(BigDecimal totalIngresos) { this.totalIngresos = totalIngresos; }

    public BigDecimal getPromedioVenta() { return promedioVenta; }
    public void setPromedioVenta(BigDecimal promedioVenta) { this.promedioVenta = promedioVenta; }

    public List<VentaResumenDTO> getVentas() { return ventas; }
    public void setVentas(List<VentaResumenDTO> ventas) { this.ventas = ventas; }

    public List<ProductoMasVendidoDTO> getProductosMasVendidos() { return productosMasVendidos; }
    public void setProductosMasVendidos(List<ProductoMasVendidoDTO> productosMasVendidos) { this.productosMasVendidos = productosMasVendidos; }

    public List<VentasPorDiaDTO> getVentasPorDia() { return ventasPorDia; }
    public void setVentasPorDia(List<VentasPorDiaDTO> ventasPorDia) { this.ventasPorDia = ventasPorDia; }

    // Clases internas para el reporte
    public static class VentaResumenDTO {
        private String id;
        private String numeroVenta;
        private LocalDateTime fechaVenta;
        private BigDecimal total;
        private String estado;
        private String clienteNombre;

        public VentaResumenDTO() {}

        public VentaResumenDTO(String id, String numeroVenta, LocalDateTime fechaVenta,
                              BigDecimal total, String estado, String clienteNombre) {
            this.id = id;
            this.numeroVenta = numeroVenta;
            this.fechaVenta = fechaVenta;
            this.total = total;
            this.estado = estado;
            this.clienteNombre = clienteNombre;
        }

        // Getters y Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getNumeroVenta() { return numeroVenta; }
        public void setNumeroVenta(String numeroVenta) { this.numeroVenta = numeroVenta; }

        public LocalDateTime getFechaVenta() { return fechaVenta; }
        public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        public String getClienteNombre() { return clienteNombre; }
        public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    }

    public static class ProductoMasVendidoDTO {
        private String nombreProducto;
        private int cantidadVendida;
        private BigDecimal ingresosGenerados;

        public ProductoMasVendidoDTO() {}

        public ProductoMasVendidoDTO(String nombreProducto, int cantidadVendida, BigDecimal ingresosGenerados) {
            this.nombreProducto = nombreProducto;
            this.cantidadVendida = cantidadVendida;
            this.ingresosGenerados = ingresosGenerados;
        }

        // Getters y Setters
        public String getNombreProducto() { return nombreProducto; }
        public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

        public int getCantidadVendida() { return cantidadVendida; }
        public void setCantidadVendida(int cantidadVendida) { this.cantidadVendida = cantidadVendida; }

        public BigDecimal getIngresosGenerados() { return ingresosGenerados; }
        public void setIngresosGenerados(BigDecimal ingresosGenerados) { this.ingresosGenerados = ingresosGenerados; }
    }

    public static class VentasPorDiaDTO {
        private LocalDateTime fecha;
        private int numeroVentas;
        private BigDecimal totalVentas;

        public VentasPorDiaDTO() {}

        public VentasPorDiaDTO(LocalDateTime fecha, int numeroVentas, BigDecimal totalVentas) {
            this.fecha = fecha;
            this.numeroVentas = numeroVentas;
            this.totalVentas = totalVentas;
        }

        // Getters y Setters
        public LocalDateTime getFecha() { return fecha; }
        public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

        public int getNumeroVentas() { return numeroVentas; }
        public void setNumeroVentas(int numeroVentas) { this.numeroVentas = numeroVentas; }

        public BigDecimal getTotalVentas() { return totalVentas; }
        public void setTotalVentas(BigDecimal totalVentas) { this.totalVentas = totalVentas; }
    }
}