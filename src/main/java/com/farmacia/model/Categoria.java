package com.farmacia.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "categorias")
public class Categoria {
    @Id
    private String id;
    
    private String nombre;
    private String descripcion;
    private String imagen;
    private boolean activa = true;
    
    @CreatedDate
    private LocalDateTime fechaCreacion;
    
    @LastModifiedDate
    private LocalDateTime fechaActualizacion;
}