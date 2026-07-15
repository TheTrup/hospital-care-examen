package com.hospital.msvc_hospitalizacion.service;

import com.hospital.msvc_hospitalizacion.client.MedicoClient;
import com.hospital.msvc_hospitalizacion.client.PacienteClient;
import com.hospital.msvc_hospitalizacion.dto.HospitalizacionDTO;
import com.hospital.msvc_hospitalizacion.dto.external.MedicoDTO;
import com.hospital.msvc_hospitalizacion.dto.external.PacienteDTO;
import com.hospital.msvc_hospitalizacion.model.Hospitalizacion;
import com.hospital.msvc_hospitalizacion.repository.HospitalizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalizacionServiceImpl implements HospitalizacionService {

    @Autowired
    private HospitalizacionRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    @Override
    public HospitalizacionDTO registrarIngreso(HospitalizacionDTO dto) {
        // 1. Validar Paciente por Feign
        PacienteDTO paciente;
        try {
            paciente = pacienteClient.obtenerPacientePorId(dto.getPacienteId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Paciente con ID " + dto.getPacienteId() + " no existe.");
        }

        // 2. Validar Médico por Feign
        MedicoDTO medico;
        try {
            medico = medicoClient.obtenerMedicoPorId(dto.getMedicoId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Médico con ID " + dto.getMedicoId() + " no existe.");
        }

        // 3. Registrar Hospitalización
        Hospitalizacion hospitalizacion = Hospitalizacion.builder()
                .pacienteId(paciente.getId())
                .medicoId(medico.getId())
                .fechaIngreso(LocalDateTime.now())
                .sala(dto.getSala())
                .cama(dto.getCama())
                .motivoIngreso(dto.getMotivoIngreso())
                .estado("INGRESADO")
                .build();

        Hospitalizacion guardada = repository.save(hospitalizacion);

        return construirDTO(guardada, paciente, medico);
    }

    @Override
    public HospitalizacionDTO darDeAlta(Long id) {
        Hospitalizacion hosp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: La Hospitalización con ID " + id + " no existe."));

        hosp.setFechaAlta(LocalDateTime.now());
        hosp.setEstado("DE_ALTA");
        
        Hospitalizacion guardada = repository.save(hosp);
        return completarHospConFeign(guardada);
    }

    @Override
    public List<HospitalizacionDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::completarHospConFeign)
                .collect(Collectors.toList());
    }

    @Override
    public List<HospitalizacionDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(this::completarHospConFeign)
                .collect(Collectors.toList());
    }

    // Auxiliar para rellenar datos usando Feign
    private HospitalizacionDTO completarHospConFeign(Hospitalizacion hosp) {
        PacienteDTO paciente = null;
        MedicoDTO medico = null;

        try { paciente = pacienteClient.obtenerPacientePorId(hosp.getPacienteId()); } catch (Exception ignored) {}
        try { medico = medicoClient.obtenerMedicoPorId(hosp.getMedicoId()); } catch (Exception ignored) {}

        return construirDTO(hosp, paciente, medico);
    }

    // Auxiliar de mapeo
    private HospitalizacionDTO construirDTO(Hospitalizacion hosp, PacienteDTO paciente, MedicoDTO medico) {
        return HospitalizacionDTO.builder()
                .id(hosp.getId())
                .pacienteId(hosp.getPacienteId())
                .medicoId(hosp.getMedicoId())
                .fechaIngreso(hosp.getFechaIngreso())
                .fechaAlta(hosp.getFechaAlta())
                .sala(hosp.getSala())
                .cama(hosp.getCama())
                .motivoIngreso(hosp.getMotivoIngreso())
                .estado(hosp.getEstado())
                .paciente(paciente)
                .medico(medico)
                .build();
    }
}
