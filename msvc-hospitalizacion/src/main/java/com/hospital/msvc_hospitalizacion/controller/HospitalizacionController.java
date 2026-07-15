package com.hospital.msvc_hospitalizacion.controller;

import com.hospital.msvc_hospitalizacion.dto.HospitalizacionDTO;
import com.hospital.msvc_hospitalizacion.service.HospitalizacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitalizaciones")
public class HospitalizacionController {

    @Autowired
    private HospitalizacionService service;

    @PostMapping
    public ResponseEntity<HospitalizacionDTO> ingresar(@Valid @RequestBody HospitalizacionDTO dto) {
        return new ResponseEntity<>(service.registrarIngreso(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/alta")
    public ResponseEntity<HospitalizacionDTO> darDeAlta(@PathVariable Long id) {
        return ResponseEntity.ok(service.darDeAlta(id));
    }

    @GetMapping
    public ResponseEntity<List<HospitalizacionDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HospitalizacionDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}