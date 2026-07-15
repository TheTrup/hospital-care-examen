package com.hospital.msvc_fichas.repository;

import com.hospital.msvc_fichas.model.FichaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FichaRepository extends JpaRepository<FichaClinica, Long>{
    List<FichaClinica> findByPacienteId(Long pacienteId);

}
