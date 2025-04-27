package com.architecture.backend_architecture.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "obra")
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String ubicacion;
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;
    private String descripcion;

    public Obra(){}
}
