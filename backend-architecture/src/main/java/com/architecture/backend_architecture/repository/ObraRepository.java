package com.architecture.backend_architecture.repository;

import com.architecture.backend_architecture.model.NombreRol;
import com.architecture.backend_architecture.model.Obra;
import com.architecture.backend_architecture.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

}
