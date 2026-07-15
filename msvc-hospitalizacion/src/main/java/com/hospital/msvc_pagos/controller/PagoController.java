package com.hospital.msvc_pagos.controller;

import com.hospital.msvc_pagos.dto.PagoDTO;
import com.hospital.msvc_pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @PostMapping
    public ResponseEntity<PagoDTO> pagar(@Valid @RequestBody PagoDTO dto) {
        return new ResponseEntity<>(service.procesarPago(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<PagoDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}