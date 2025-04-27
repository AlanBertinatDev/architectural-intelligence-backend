package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.model.Obra;

import java.util.List;

public interface ObraService {
    Obra crear(Obra obra);
    List<Obra> listar();
    Obra obtenerPorId(Long id);
    Obra actualizar(Long id, Obra obra);
    void eliminar(Long id);
}
