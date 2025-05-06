package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.EmpleadoAdjunto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmpleadoAdjuntoService {

    List<EmpleadoAdjunto> obtenerAdjuntosPorEmpleado(Long empleadoId);

    Resource obtenerArchivoPorId(Integer adjuntoId) ;
}
