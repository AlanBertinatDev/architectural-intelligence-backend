package com.architecture.backend_architecture.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "empleado_adjunto")
public class EmpleadoAdjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Column(name = "ruta_archivo")
    private String rutaArchivo;
    @Column(name = "tipo_archivo")
    private String tipoArchivo;
    @Column(name = "tamanio_archivo")
    private Long tamanioArchivo;
    private Boolean activo = true;
    @Column(name = "fecha_subida")
    private Date fechaSubida = new Date();
}