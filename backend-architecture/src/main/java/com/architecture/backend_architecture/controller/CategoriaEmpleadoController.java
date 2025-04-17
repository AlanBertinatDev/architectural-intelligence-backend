package com.architecture.backend_architecture.controller;

import com.architecture.backend_architecture.model.CategoriaEmpleado;
import com.architecture.backend_architecture.service.CategoriaEmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-empleado")
public class CategoriaEmpleadoController {

    private final CategoriaEmpleadoService service;

    public CategoriaEmpleadoController(CategoriaEmpleadoService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoriaEmpleado> crear(@RequestBody CategoriaEmpleado categoria) {
        return ResponseEntity.ok(service.crear(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaEmpleado>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEmpleado> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaEmpleado> actualizar(@PathVariable Long id, @RequestBody CategoriaEmpleado categoria) {
        return ResponseEntity.ok(service.actualizar(id, categoria));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}