package com.farmacia.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "productos")
public class Producto {
    @Id
    private String id;
    
    private String nombre;
    private String descripcion;
    private String ingredientesActivos;
    private String instruccionesUso;
    private BigDecimal precio;
    private String imagen;
    private boolean requiereReceta;
    private boolean activo = true;
    private Integer stock;
    private String codigoBarras;
    private String laboratorio;
    private String presentacion;
    
    @DBRef
    private Categoria categoria;
    
    private List<String> contraindicaciones;
    private List<String> efectosSecundarios;
    
    @CreatedDate
    private LocalDateTime fechaCreacion;
    
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;
}