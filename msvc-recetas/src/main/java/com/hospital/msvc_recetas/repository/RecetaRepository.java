package com.hospital.msvc_recetas.repository;

import com.hospital.msvc_recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    // Permite listar todas las recetas asociadas a un paciente específico
    List<Receta> findByPacienteId(Long pacienteId);
}
