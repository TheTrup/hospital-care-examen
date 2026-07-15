package com.hospital.msvc_urgencias.controller;

import com.hospital.msvc_urgencias.dto.UrgenciaDTO;
import com.hospital.msvc_urgencias.service.UrgenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urgencias")
public class UrgenciaController {

    @Autowired
    private UrgenciaService service;

    @PostMapping
    public ResponseEntity<UrgenciaDTO> ingresar(@Valid @RequestBody UrgenciaDTO dto) {
        return new ResponseEntity<>(service.registrarIngresoUrgencia(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/egreso")
    public ResponseEntity<UrgenciaDTO> egresar(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(service.actualizarEstadoEgreso(id, nuevoEstado));
    }

    @GetMapping
    public ResponseEntity<List<UrgenciaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<UrgenciaDTO>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }
}