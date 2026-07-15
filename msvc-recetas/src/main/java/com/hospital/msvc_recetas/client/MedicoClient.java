package com.hospital.msvc_recetas.client;

import com.hospital.msvc_recetas.dto.external.MedicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-medicos")
public interface MedicoClient {

    @GetMapping("/api/v1/medicos/{id}")
    MedicoDTO obtenerMedicoPorId(@PathVariable("id") Long id);
}