package com.hospital.msvc_examenes.client;

import com.hospital.msvc_examenes.dto.external.PacienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-pacientes")
public interface PacienteClient {

    @GetMapping("/api/v1/pacientes/{id}")
    PacienteDTO obtenerPacientePorId(@PathVariable("id") Long id);
}