package com.architecture.backend_architecture.repository;

import com.architecture.backend_architecture.model.CategoriaEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaEmpleadoRepository extends JpaRepository<CategoriaEmpleado, Long> {
    boolean existsByNombre(String nombre);

}
