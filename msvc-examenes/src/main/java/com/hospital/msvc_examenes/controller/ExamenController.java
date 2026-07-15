package com.hospital.msvc_examenes.controller;

import com.hospital.msvc_examenes.dto.ExamenDTO;
import com.hospital.msvc_examenes.service.ExamenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/examenes")
public class ExamenController {

    @Autowired
    private ExamenService service;

    @PostMapping
    public ResponseEntity<ExamenDTO> solicitar(@Valid @RequestBody ExamenDTO dto) {
        return new ResponseEntity<>(service.solicitarExamen(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/resultado")
    public ResponseEntity<ExamenDTO> registrarResultado(@PathVariable Long id, @RequestBody String resultado) {
        return ResponseEntity.ok(service.registrarResultado(id, resultado));
    }

    @GetMapping
    public ResponseEntity<List<ExamenDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExamenDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}