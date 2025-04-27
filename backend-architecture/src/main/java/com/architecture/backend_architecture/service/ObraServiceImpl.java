package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.Obra;
import com.architecture.backend_architecture.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraServiceImpl implements ObraService{
    private final ObraRepository repository;

    public ObraServiceImpl(ObraRepository repository) {
        this.repository = repository;
    }

    @Override
    public Obra crear(Obra obra) {
        return repository.save(obra);
    }

    @Override
    public List<Obra> listar() {
        return repository.findAll();
    }

    @Override
    public Obra obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Obra no encontrada"));
    }

    @Override
    public Obra actualizar(Long id, Obra obra) {
        Obra existente = obtenerPorId(id);
        existente.setNombre(obra.getNombre());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
