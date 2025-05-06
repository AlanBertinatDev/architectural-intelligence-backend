package com.architecture.backend_architecture.controller;

import com.architecture.backend_architecture.dto.AdjuntoDTO;
import com.architecture.backend_architecture.model.Empleado;
import com.architecture.backend_architecture.model.EmpleadoAdjunto;
import com.architecture.backend_architecture.service.EmpleadoAdjuntoService;
import com.architecture.backend_architecture.service.EmpleadoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "Bearer")
@Tag(name = "Gestión de empleados", description = "Crud de Empleados")
@RequestMapping("/api/empleados")
public class EmpleadoController {
    @Autowired
    private final EmpleadoService service;

    @Autowired
    private final EmpleadoAdjuntoService empleadoAdjuntoService;

    public EmpleadoController(EmpleadoService service, EmpleadoAdjuntoService empleadoAdjuntoService) {
        this.service = service;
        this.empleadoAdjuntoService = empleadoAdjuntoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmpleado(
            @RequestParam("empleado") String empleadoJson,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {

        try {
            // Convertir el JSON de empleado a objeto Empleado
            Empleado empleado = new ObjectMapper().readValue(empleadoJson, Empleado.class);

            // Verificar si se recibieron archivos
            if (files != null && files.length > 0) {
                System.out.println("Archivos recibidos: " + files.length);
                // Llamar al servicio para guardar el empleado y los archivos adjuntos
                Empleado savedEmpleado = service.saveEmpleadoWithFiles(empleado, files);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpleado);
            } else {
                // Si no se recibieron archivos, solo guardar el empleado
                Empleado savedEmpleado = service.saveEmpleado(empleado);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpleado);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Esto te dará más detalles sobre cualquier excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el empleado");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(
            @PathVariable Long id,
            @RequestParam("empleado") String empleadoJson,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "idsAdjuntosAEliminar", required = false) String idsAdjuntosAEliminarJson
    ) {
        try {
            Empleado empleado = new ObjectMapper().readValue(empleadoJson, Empleado.class);

            List<Integer> idsAdjuntosAEliminar = new ArrayList<>();
            if (idsAdjuntosAEliminarJson != null && !idsAdjuntosAEliminarJson.isEmpty()) {
                idsAdjuntosAEliminar = new ObjectMapper().readValue(
                        idsAdjuntosAEliminarJson,
                        new TypeReference<List<Integer>>() {}
                );
            }

            Empleado updatedEmpleado = service.updateEmpleadoWithFiles(id, empleado, files, idsAdjuntosAEliminar);
            return ResponseEntity.ok(updatedEmpleado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el empleado");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/adjuntos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdjuntoDTO>> obtenerAdjuntosDeEmpleado(@PathVariable Long id) {
        List<EmpleadoAdjunto> adjuntos = empleadoAdjuntoService.obtenerAdjuntosPorEmpleado(id);

        List<AdjuntoDTO> adjuntosDTO = adjuntos.stream()
                .map(a -> new AdjuntoDTO(a.getId(), a.getNombreArchivo()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(adjuntosDTO);
    }

    @GetMapping("/adjuntos/{id}")
    public ResponseEntity<Resource> descargarAdjunto(@PathVariable Integer id) {
        Resource archivo = empleadoAdjuntoService.obtenerArchivoPorId(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo.getFilename() + "\"")
                .body(archivo);
    }
}
