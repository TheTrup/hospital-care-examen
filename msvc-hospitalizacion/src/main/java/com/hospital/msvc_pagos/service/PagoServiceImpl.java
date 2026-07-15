package com.hospital.msvc_pagos.service;

import com.hospital.msvc_pagos.client.PacienteClient;
import com.hospital.msvc_pagos.dto.PagoDTO;
import com.hospital.msvc_pagos.dto.external.PacienteDTO;
import com.hospital.msvc_pagos.model.Pago;
import com.hospital.msvc_pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Override
    public PagoDTO procesarPago(PagoDTO dto) {
        // 1. Validar Paciente por Feign
        PacienteDTO paciente;
        try {
            paciente = pacienteClient.obtenerPacientePorId(dto.getPacienteId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Paciente con ID " + dto.getPacienteId() + " no existe.");
        }

        // 2. Registrar el Pago
        Pago pago = Pago.builder()
                .pacienteId(paciente.getId())
                .monto(dto.getMonto())
                .metodoPago(dto.getMetodoPago())
                .fechaPago(LocalDateTime.now())
                .estado("PROCESADO")
                .build();

        Pago guardado = repository.save(pago);

        return construirDTO(guardado, paciente);
    }

    @Override
    public List<PagoDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::completarPagoConFeign)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(this::completarPagoConFeign)
                .collect(Collectors.toList());
    }

    // Auxiliar de Feign
    private PagoDTO completarPagoConFeign(Pago pago) {
        PacienteDTO paciente = null;
        try {
            paciente = pacienteClient.obtenerPacientePorId(pago.getPacienteId());
        } catch (Exception ignored) {}

        return construirDTO(pago, paciente);
    }

    // Auxiliar de mapeo
    private PagoDTO construirDTO(Pago pago, PacienteDTO paciente) {
        return PagoDTO.builder()
                .id(pago.getId())
                .pacienteId(pago.getPacienteId())
                .monto(pago.getMonto())
                .metodoPago(pago.getMetodoPago())
                .fechaPago(pago.getFechaPago())
                .estado(pago.getEstado())
                .paciente(paciente)
                .build();
    }
}