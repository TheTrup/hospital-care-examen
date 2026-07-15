package com.hospital.msvc_especialidades.controller;

import com.hospital.msvc_especialidades.dto.EspecialidadDTO;
import com.hospital.msvc_especialidades.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    @PostMapping
    public ResponseEntity<EspecialidadDTO> guardar(@Valid @RequestBody EspecialidadDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}