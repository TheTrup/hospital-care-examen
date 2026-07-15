package com.hospital.msvc_recetas.controller;

import com.hospital.msvc_recetas.dto.RecetaDTO;
import com.hospital.msvc_recetas.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    @Autowired
    private RecetaService service;

    @PostMapping
    public ResponseEntity<RecetaDTO> crear(@Valid @RequestBody RecetaDTO dto) {
        return new ResponseEntity<>(service.crearReceta(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<RecetaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}