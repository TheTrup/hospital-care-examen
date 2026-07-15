package com.hospital.msvc_citas.controller;

import com.hospital.msvc_citas.dto.CitaDTO;
import com.hospital.msvc_citas.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas")

public class CitaController {

    @Autowired
    private CitaService service;

    @PostMapping
    public ResponseEntity<CitaDTO> agendar(@Valid @RequestBody CitaDTO dto) {
        return new ResponseEntity<>(service.agendarCita(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

}
