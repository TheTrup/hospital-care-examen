package com.hospital.msvc_examenes.repository;

import com.hospital.msvc_examenes.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    // Permite listar el historial de exámenes de un paciente específico
    List<Examen> findByPacienteId(Long pacienteId);
}