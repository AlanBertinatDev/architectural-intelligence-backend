package com.architecture.backend_architecture.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int cedula;
    private String nombre;
    private String apellido;
    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;
    private boolean activo;
    @Column(name = "obra_id", nullable = false)
    private int idObra;
    public Empleado(){}
}
