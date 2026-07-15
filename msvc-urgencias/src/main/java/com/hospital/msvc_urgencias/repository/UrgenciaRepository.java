package com.hospital.msvc_urgencias.repository;

import com.hospital.msvc_urgencias.model.Urgencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrgenciaRepository extends JpaRepository<Urgencia, Long> {
    // Permite listar los ingresos de urgencia de un paciente específico
    List<Urgencia> findByPacienteId(Long pacienteId);
}
