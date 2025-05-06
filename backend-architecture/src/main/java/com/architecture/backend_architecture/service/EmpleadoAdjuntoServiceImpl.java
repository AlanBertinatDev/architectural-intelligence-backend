package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.EmpleadoAdjunto;
import com.architecture.backend_architecture.repository.EmpleadoAdjuntoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

@Service
public class EmpleadoAdjuntoServiceImpl implements EmpleadoAdjuntoService{

    private final EmpleadoAdjuntoRepository empleadoAdjuntoRepository;

    public EmpleadoAdjuntoServiceImpl(EmpleadoAdjuntoRepository empleadoAdjuntoRepository) {
        this.empleadoAdjuntoRepository = empleadoAdjuntoRepository;
    }

    @Override
    public List<EmpleadoAdjunto> obtenerAdjuntosPorEmpleado(Long empleadoId) {
        return empleadoAdjuntoRepository.findByEmpleadoIdAndActivoTrue(empleadoId);
    }

    @Override
    public Resource obtenerArchivoPorId(Integer adjuntoId) {
        EmpleadoAdjunto adjunto = empleadoAdjuntoRepository.findById(adjuntoId)
                .orElseThrow(() -> new RuntimeException("Adjunto no encontrado"));

        Path path = Paths.get(adjunto.getRutaArchivo()); // Suponiendo que guardás ruta
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al cargar archivo", e);
        }
    }
}
