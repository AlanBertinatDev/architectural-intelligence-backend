package com.architecture.backend_architecture.controller;

import com.architecture.backend_architecture.model.Obra;
import com.architecture.backend_architecture.service.ObraService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer")
@Tag(name = "Gestión Obras", description = "Crud de Obras")
@RequestMapping("/api/obras")
public class ObraController {
    private final ObraService service;

    public ObraController(ObraService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Obra> crear(@RequestBody Obra obra) {
        return ResponseEntity.ok(service.crear(obra));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Obra>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Obra> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Obra> actualizar(@PathVariable Long id, @RequestBody Obra obra) {
        return ResponseEntity.ok(service.actualizar(id, obra));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
