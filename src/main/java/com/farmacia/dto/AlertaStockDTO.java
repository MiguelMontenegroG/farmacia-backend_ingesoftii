package com.farmacia.dto;

public class AlertaStockDTO {
    private String productoId;
    private String nombreProducto;
    private int stockActual;
    private int stockMinimo;
    private String nivelAlerta;
    private String mensaje;

    // Constructores
    public AlertaStockDTO() {}

    public AlertaStockDTO(String productoId, String nombreProducto, int stockActual,
                          int stockMinimo, String nivelAlerta, String mensaje) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.nivelAlerta = nivelAlerta;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getNivelAlerta() { return nivelAlerta; }
    public void setNivelAlerta(String nivelAlerta) { this.nivelAlerta = nivelAlerta; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}