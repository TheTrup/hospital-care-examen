package com.hospital.msvc_hospitalizacion.repository;

import com.hospital.msvc_hospitalizacion.model.Hospitalizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalizacionRepository extends JpaRepository<Hospitalizacion, Long> {
    // Permite buscar el historial de hospitalizaciones de un paciente específico
    List<Hospitalizacion> findByPacienteId(Long pacienteId);
}