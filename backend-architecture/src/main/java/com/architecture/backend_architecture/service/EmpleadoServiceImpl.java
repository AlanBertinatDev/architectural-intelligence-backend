package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.Empleado;
import com.architecture.backend_architecture.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl  implements EmpleadoService{
    private final EmpleadoRepository repository;

    public EmpleadoServiceImpl(EmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empleado crear(Empleado empleado) {
        return repository.save(empleado);
    }

    @Override
    public List<Empleado> listar() {
        return repository.findAll();
    }

    @Override
    public Empleado obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public Empleado actualizar(Long id, Empleado empleado) {
        Empleado existente = obtenerPorId(id);
        existente.setNombre(empleado.getNombre());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
