package com.hospital.msvc_pagos.repository;

import com.hospital.msvc_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByPacienteId(Long pacienteId);
}