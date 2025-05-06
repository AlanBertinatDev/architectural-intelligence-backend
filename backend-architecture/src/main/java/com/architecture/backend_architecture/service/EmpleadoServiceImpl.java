package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.Empleado;
import com.architecture.backend_architecture.model.EmpleadoAdjunto;
import com.architecture.backend_architecture.repository.EmpleadoAdjuntoRepository;
import com.architecture.backend_architecture.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoAdjuntoRepository empleadoAdjuntoRepository;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, EmpleadoAdjuntoRepository empleadoAdjuntoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoAdjuntoRepository = empleadoAdjuntoRepository;
    }

    @Override
    public Empleado saveEmpleadoWithFiles(Empleado empleado, MultipartFile[] files) throws IOException {
        Empleado savedEmpleado = empleadoRepository.save(empleado);

        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    saveEmpleadoAdjunto(savedEmpleado, file);
                } catch (IOException e) {
                    logger.error("Error guardando archivo {} para el empleado {}", file.getOriginalFilename(), savedEmpleado.getId(), e);
                }
            }
        }

        return savedEmpleado;
    }

    private void saveEmpleadoAdjunto(Empleado empleado, MultipartFile file) throws IOException {
        // Crear subdirectorio por empleado
        Path empleadoDir = Paths.get(uploadDirectory, "empleado_" + empleado.getId());
        File dir = empleadoDir.toFile();
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logger.info("Directorio creado para empleado: {}", empleado.getId());
            } else {
                logger.error("No se pudo crear directorio para empleado: {}", empleado.getId());
                throw new IOException("No se pudo crear directorio para subir archivos");
            }
        }

        // Generar nombre de archivo único
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        Path filePath = empleadoDir.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());

        // Guardar info en la tabla empleado_adjunto
        EmpleadoAdjunto adjunto = new EmpleadoAdjunto();
        adjunto.setEmpleado(empleado);
        adjunto.setNombreArchivo(uniqueFileName);
        adjunto.setRutaArchivo(filePath.toString());
        adjunto.setTipoArchivo(file.getContentType());
        adjunto.setTamanioArchivo(file.getSize());

        empleadoAdjuntoRepository.save(adjunto);

        logger.info("Archivo {} guardado exitosamente para empleado {}", uniqueFileName, empleado.getId());
    }

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado obtenerPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
    }

    @Override
    public Empleado updateEmpleado(Long id, Empleado empleadoActualizado) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        // Actualizar todos los campos relevantes
        empleadoExistente.setCedula(empleadoActualizado.getCedula());
        empleadoExistente.setNombreCompleto(empleadoActualizado.getNombreCompleto());
        empleadoExistente.setFechaNacimiento(empleadoActualizado.getFechaNacimiento());
        empleadoExistente.setFechaIngreso(empleadoActualizado.getFechaIngreso());
        empleadoExistente.setEstadoCivil(empleadoActualizado.getEstadoCivil());
        empleadoExistente.setDomicilio(empleadoActualizado.getDomicilio());
        empleadoExistente.setCorreo(empleadoActualizado.getCorreo());
        empleadoExistente.setObraId(empleadoActualizado.getObraId());

        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    public Empleado updateEmpleadoWithFiles(Long id, Empleado empleadoActualizado, MultipartFile[] files, List<Integer> idsAdjuntosAEliminar) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        // Actualizar campos del empleado
        empleadoExistente.setCedula(empleadoActualizado.getCedula());
        empleadoExistente.setNombreCompleto(empleadoActualizado.getNombreCompleto());
        empleadoExistente.setFechaNacimiento(empleadoActualizado.getFechaNacimiento());
        empleadoExistente.setFechaIngreso(empleadoActualizado.getFechaIngreso());
        empleadoExistente.setEstadoCivil(empleadoActualizado.getEstadoCivil());
        empleadoExistente.setDomicilio(empleadoActualizado.getDomicilio());
        empleadoExistente.setCorreo(empleadoActualizado.getCorreo());
        empleadoExistente.setObraId(empleadoActualizado.getObraId());

        // 1. Baja lógica a archivos marcados para eliminar
        if (idsAdjuntosAEliminar != null && !idsAdjuntosAEliminar.isEmpty()) {
            for (Integer adjuntoId : idsAdjuntosAEliminar) {
                empleadoAdjuntoRepository.findById(adjuntoId).ifPresent(adjunto -> {
                    adjunto.setActivo(false);
                    empleadoAdjuntoRepository.save(adjunto);
                });
            }
        }

        // 2. Guardar nuevos archivos
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                try {
                    saveEmpleadoAdjunto(empleadoExistente, file);
                } catch (IOException e) {
                    logger.error("Error guardando archivo {} para empleado {}", file.getOriginalFilename(), empleadoExistente.getId(), e);
                }
            }
        }

        return empleadoRepository.save(empleadoExistente);
    }

    private void deleteAdjuntosFisicosYDb(Long empleadoId) {
        List<EmpleadoAdjunto> adjuntos = empleadoAdjuntoRepository.findByEmpleadoId(empleadoId);

        for (EmpleadoAdjunto adjunto : adjuntos) {
            File archivo = new File(adjunto.getRutaArchivo());
            if (archivo.exists()) {
                if (archivo.delete()) {
                    logger.info("Archivo físico eliminado: {}", adjunto.getRutaArchivo());
                } else {
                    logger.error("No se pudo eliminar archivo físico: {}", adjunto.getRutaArchivo());
                }
            }
        }

        empleadoAdjuntoRepository.deleteByEmpleadoId(empleadoId);
    }

    @Override
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}