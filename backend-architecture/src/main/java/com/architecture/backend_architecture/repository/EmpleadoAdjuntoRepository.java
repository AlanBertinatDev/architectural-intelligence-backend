package com.architecture.backend_architecture.repository;

import com.architecture.backend_architecture.model.EmpleadoAdjunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface EmpleadoAdjuntoRepository extends JpaRepository<EmpleadoAdjunto, Integer> {

    List<EmpleadoAdjunto> findByEmpleadoIdAndActivoTrue(Long empleadoId);

    // Buscar por empleadoId
    List<EmpleadoAdjunto> findByEmpleadoId(Long empleadoId);

    // Borrar por empleadoId
    @Transactional
    @Modifying
    @Query("DELETE FROM EmpleadoAdjunto ea WHERE ea.empleado.id = :empleadoId")
    void deleteByEmpleadoId(Long empleadoId);
}