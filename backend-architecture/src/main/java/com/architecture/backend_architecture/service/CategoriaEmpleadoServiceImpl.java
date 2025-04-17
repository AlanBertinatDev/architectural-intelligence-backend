package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.CategoriaEmpleado;
import com.architecture.backend_architecture.repository.CategoriaEmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaEmpleadoServiceImpl implements CategoriaEmpleadoService {

    private final CategoriaEmpleadoRepository repository;

    public CategoriaEmpleadoServiceImpl(CategoriaEmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoriaEmpleado crear(CategoriaEmpleado categoria) {
        return repository.save(categoria);
    }

    @Override
    public List<CategoriaEmpleado> listar() {
        return repository.findAll();
    }

    @Override
    public CategoriaEmpleado obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Override
    public CategoriaEmpleado actualizar(Long id, CategoriaEmpleado categoria) {
        CategoriaEmpleado existente = obtenerPorId(id);
        existente.setNombre(categoria.getNombre());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}