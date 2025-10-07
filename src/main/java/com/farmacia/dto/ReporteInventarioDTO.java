package com.farmacia.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ReporteInventarioDTO {
    private Map<String, Long> totalPorCategoria;
    private int totalProductos;
    private int productosStockBajo;
    private int productosSinStock;
    private List<ProductoReporteDTO> topProductosBajoStock;
    private BigDecimal valorTotalInventario;

    // Constructores
    public ReporteInventarioDTO() {}

    public ReporteInventarioDTO(int totalProductos, int productosStockBajo,
                                int productosSinStock, BigDecimal valorTotalInventario) {
        this.totalProductos = totalProductos;
        this.productosStockBajo = productosStockBajo;
        this.productosSinStock = productosSinStock;
        this.valorTotalInventario = valorTotalInventario;
    }

    // Getters y Setters
    public Map<String, Long> getTotalPorCategoria() { return totalPorCategoria; }
    public void setTotalPorCategoria(Map<String, Long> totalPorCategoria) {
        this.totalPorCategoria = totalPorCategoria;
    }

    public int getTotalProductos() { return totalProductos; }
    public void setTotalProductos(int totalProductos) { this.totalProductos = totalProductos; }

    public int getProductosStockBajo() { return productosStockBajo; }
    public void setProductosStockBajo(int productosStockBajo) { this.productosStockBajo = productosStockBajo; }

    public int getProductosSinStock() { return productosSinStock; }
    public void setProductosSinStock(int productosSinStock) { this.productosSinStock = productosSinStock; }

    public List<ProductoReporteDTO> getTopProductosBajoStock() { return topProductosBajoStock; }
    public void setTopProductosBajoStock(List<ProductoReporteDTO> topProductosBajoStock) {
        this.topProductosBajoStock = topProductosBajoStock;
    }

    public BigDecimal getValorTotalInventario() { return valorTotalInventario; }
    public void setValorTotalInventario(BigDecimal valorTotalInventario) {
        this.valorTotalInventario = valorTotalInventario;
    }
}
