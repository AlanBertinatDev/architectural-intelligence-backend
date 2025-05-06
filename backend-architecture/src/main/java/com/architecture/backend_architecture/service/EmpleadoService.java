package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.Empleado;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmpleadoService {
    Empleado saveEmpleadoWithFiles(Empleado empleado, MultipartFile[] files) throws IOException;

    Empleado saveEmpleado(Empleado empleado);

    Empleado updateEmpleado(Long id, Empleado empleado);
    Empleado updateEmpleadoWithFiles(Long id, Empleado empleadoActualizado, MultipartFile[] files, List<Integer> idsAdjuntosAEliminar);
    List<Empleado> listar();
    Empleado obtenerPorId(Long id);
    void eliminar(Long id);
}
