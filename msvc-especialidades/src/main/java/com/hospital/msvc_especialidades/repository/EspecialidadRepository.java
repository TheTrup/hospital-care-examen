package com.hospital.msvc_especialidades.repository;

import com.hospital.msvc_especialidades.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    // Permite buscar por nombre exacto para evitar duplicar especialidades
    Optional<Especialidad> findByNombreIgnoreCase(String nombre);
}
