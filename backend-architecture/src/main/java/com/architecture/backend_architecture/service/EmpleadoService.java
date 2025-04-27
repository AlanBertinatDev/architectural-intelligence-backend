package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado crear(Empleado empleado);
    List<Empleado> listar();
    Empleado obtenerPorId(Long id);
    Empleado actualizar(Long id, Empleado empleado);
    void eliminar(Long id);
}
