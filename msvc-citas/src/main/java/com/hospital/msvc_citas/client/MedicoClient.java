package com.hospital.msvc_citas.client;

import com.hospital.msvc_citas.dto.external.MedicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-medicos", url = "http://localhost:8082")
public interface MedicoClient {

    @GetMapping("/api/v1/medicos/{id}")
    MedicoDTO obtenerMedicoPorId(@PathVariable("id") Long id);

}
