package com.hospital.msvc_citas.service;


import com.hospital.msvc_citas.client.MedicoClient;
import com.hospital.msvc_citas.client.PacienteClient;
import com.hospital.msvc_citas.dto.CitaDTO;
import com.hospital.msvc_citas.dto.external.MedicoDTO;
import com.hospital.msvc_citas.dto.external.PacienteDTO;
import com.hospital.msvc_citas.model.Cita;
import com.hospital.msvc_citas.respository.CitaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CitaServiceImpl implements CitaService{

    @Autowired
    private CitaRepository repository;

    @Autowired
    private PacienteClient pacienteClient; // Comunicación remota

    @Autowired
    private MedicoClient medicoClient;     // Comunicación remota

    @Override
    public CitaDTO agendarCita(CitaDTO dto) {
        log.info("Iniciando flujo transaccional de reserva de cita.");

        // 1. Validar remotamente la existencia del paciente
        PacienteDTO paciente;
        try {
            log.info("Llamando de forma remota a msvc-pacientes para validar ID: {}", dto.getPacienteId());
            paciente = pacienteClient.obtenerPacientePorId(dto.getPacienteId());
        } catch (Exception e) {
            log.error("Error de comunicación o Paciente no existe con ID: {}", dto.getPacienteId());
            throw new RuntimeException("No se puede agendar la cita: El Paciente ingresado no existe.");
        }

        // 2. Validar remotamente la existencia del médico
        MedicoDTO medico;
        try {
            log.info("Llamando de forma remota a msvc-medicos para validar ID: {}", dto.getMedicoId());
            medico = medicoClient.obtenerMedicoPorId(dto.getMedicoId());
        } catch (Exception e) {
            log.error("Error de comunicación o Médico no existe con ID: {}", dto.getMedicoId());
            throw new RuntimeException("No se puede agendar la cita: El Médico ingresado no existe.");
        }

        // Si ambos existen, persistimos la cita
        Cita cita = Cita.builder()
                .pacienteId(paciente.getId())
                .medicoId(medico.getId())
                .fechaHora(dto.getFechaHora())
                .estado("PENDIENTE")
                .build();

        Cita guardada = repository.save(cita);
        log.info("Cita agendada con éxito. ID de Cita: {}", guardada.getId());

        dto.setId(guardada.getId());
        dto.setEstado(guardada.getEstado());
        return dto;
    }

    @Override
    public List<CitaDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(c -> CitaDTO.builder()
                        .id(c.getId())
                        .pacienteId(c.getPacienteId())
                        .medicoId(c.getMedicoId())
                        .fechaHora(c.getFechaHora())
                        .estado(c.getEstado())
                        .build())
                .collect(Collectors.toList());
    }

    

}
