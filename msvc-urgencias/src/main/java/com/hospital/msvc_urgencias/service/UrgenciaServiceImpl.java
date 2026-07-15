package com.hospital.msvc_urgencias.service;

import com.hospital.msvc_urgencias.client.MedicoClient;
import com.hospital.msvc_urgencias.client.PacienteClient;
import com.hospital.msvc_urgencias.dto.UrgenciaDTO;
import com.hospital.msvc_urgencias.dto.external.MedicoDTO;
import com.hospital.msvc_urgencias.dto.external.PacienteDTO;
import com.hospital.msvc_urgencias.model.Urgencia;
import com.hospital.msvc_urgencias.repository.UrgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrgenciaServiceImpl implements UrgenciaService {

    @Autowired
    private UrgenciaRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    @Override
    public UrgenciaDTO registrarIngresoUrgencia(UrgenciaDTO dto) {
        // 1. Validar Paciente por Feign
        PacienteDTO paciente;
        try {
            paciente = pacienteClient.obtenerPacientePorId(dto.getPacienteId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Paciente con ID " + dto.getPacienteId() + " no existe en el sistema.");
        }

        // 2. Validar Médico de Turno por Feign
        MedicoDTO medico;
        try {
            medico = medicoClient.obtenerMedicoPorId(dto.getMedicoId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Médico con ID " + dto.getMedicoId() + " no existe en el sistema.");
        }

        // 3. Crear el Ingreso de Urgencia
        Urgencia urgencia = Urgencia.builder()
                .pacienteId(paciente.getId())
                .medicoId(medico.getId())
                .triage(dto.getTriage())
                .fechaIngreso(LocalDateTime.now())
                .motivoConsulta(dto.getMotivoConsulta())
                .estado("EN_ATENCION")
                .build();

        Urgencia guardada = repository.save(urgencia);

        return construirDTO(guardada, paciente, medico);
    }

    @Override
    public UrgenciaDTO actualizarEstadoEgreso(Long id, String nuevoEstado) {
        Urgencia urg = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El registro de urgencia con ID " + id + " no existe."));

        urg.setEstado(nuevoEstado);
        urg.setFechaEgreso(LocalDateTime.now());
        
        Urgencia guardada = repository.save(urg);
        return completarUrgenciaConFeign(guardada);
    }

    @Override
    public List<UrgenciaDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::completarUrgenciaConFeign)
                .collect(Collectors.toList());
    }

    @Override
    public List<UrgenciaDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(this::completarUrgenciaConFeign)
                .collect(Collectors.toList());
    }

    // Auxiliar de llenado usando Feign
    private UrgenciaDTO completarUrgenciaConFeign(Urgencia urgencia) {
        PacienteDTO paciente = null;
        MedicoDTO medico = null;

        try { paciente = pacienteClient.obtenerPacientePorId(urgencia.getPacienteId()); } catch (Exception ignored) {}
        try { medico = medicoClient.obtenerMedicoPorId(urgencia.getMedicoId()); } catch (Exception ignored) {}

        return construirDTO(urgencia, paciente, medico);
    }

    // Auxiliar de mapeo Entity -> DTO
    private UrgenciaDTO construirDTO(Urgencia urgencia, PacienteDTO paciente, MedicoDTO medico) {
        return UrgenciaDTO.builder()
                .id(urgencia.getId())
                .pacienteId(urgencia.getPacienteId())
                .medicoId(urgencia.getMedicoId())
                .triage(urgencia.getTriage())
                .fechaIngreso(urgencia.getFechaIngreso())
                .fechaEgreso(urgencia.getFechaEgreso())
                .motivoConsulta(urgencia.getMotivoConsulta())
                .estado(urgencia.getEstado())
                .paciente(paciente)
                .medico(medico)
                .build();
    }
}