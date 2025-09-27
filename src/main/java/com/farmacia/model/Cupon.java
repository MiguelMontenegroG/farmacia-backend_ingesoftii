package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "cupones")
public class Cupon {
    @Id
    private String id;

    @Indexed(unique = true)
    private String codigo;

    private String nombre;
    private String descripcion;
    private String tipo; // PORCENTAJE, MONTO_FIJO
    private Float valor;
    private Float montoMinimo;
    private Integer usosMaximos;
    private Integer usosActuales;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaVencimiento;
    private boolean activo;
    private List<String> categoriasAplicables;
    private List<String> productosAplicables;

    // Constructores
    public Cupon() {}

    public Cupon(String codigo, String nombre, String tipo, Float valor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.activo = true;
        this.usosActuales = 0;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Float getValor() { return valor; }
    public void setValor(Float valor) { this.valor = valor; }

    public Float getMontoMinimo() { return montoMinimo; }
    public void setMontoMinimo(Float montoMinimo) { this.montoMinimo = montoMinimo; }

    public Integer getUsosMaximos() { return usosMaximos; }
    public void setUsosMaximos(Integer usosMaximos) { this.usosMaximos = usosMaximos; }

    public Integer getUsosActuales() { return usosActuales; }
    public void setUsosActuales(Integer usosActuales) { this.usosActuales = usosActuales; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<String> getCategoriasAplicables() { return categoriasAplicables; }
    public void setCategoriasAplicables(List<String> categoriasAplicables) { this.categoriasAplicables = categoriasAplicables; }

    public List<String> getProductosAplicables() { return productosAplicables; }
    public void setProductosAplicables(List<String> productosAplicables) { this.productosAplicables = productosAplicables; }
}
