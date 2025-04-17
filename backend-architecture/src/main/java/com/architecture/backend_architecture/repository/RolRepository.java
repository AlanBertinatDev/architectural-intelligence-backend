package com.architecture.backend_architecture.repository;

import com.architecture.backend_architecture.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.architecture.backend_architecture.model.NombreRol;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(NombreRol nombre);
}