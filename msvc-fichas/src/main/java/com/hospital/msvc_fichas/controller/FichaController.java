package com.hospital.msvc_fichas.controller;

import com.hospital.msvc_fichas.dto.FichaDTO;
import com.hospital.msvc_fichas.service.FichaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fichas")
public class FichaController {

    @Autowired
    private FichaService service;

    @PostMapping
    public ResponseEntity<FichaDTO> registrar(@Valid @RequestBody FichaDTO dto) {
        return new ResponseEntity<>(service.registrarFicha(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FichaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<FichaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}