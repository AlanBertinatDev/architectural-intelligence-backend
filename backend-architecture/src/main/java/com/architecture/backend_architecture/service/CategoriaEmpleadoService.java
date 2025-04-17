package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.CategoriaEmpleado;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoriaEmpleadoService {

    CategoriaEmpleado crear(CategoriaEmpleado categoria);
    List<CategoriaEmpleado> listar();
    CategoriaEmpleado obtenerPorId(Long id);
    CategoriaEmpleado actualizar(Long id, CategoriaEmpleado categoria);
    void eliminar(Long id);
}
