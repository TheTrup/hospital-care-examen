package com.hospital.msvc_examenes.service;

import com.hospital.msvc_examenes.client.MedicoClient;
import com.hospital.msvc_examenes.client.PacienteClient;
import com.hospital.msvc_examenes.dto.ExamenDTO;
import com.hospital.msvc_examenes.dto.external.MedicoDTO;
import com.hospital.msvc_examenes.dto.external.PacienteDTO;
import com.hospital.msvc_examenes.model.Examen;
import com.hospital.msvc_examenes.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamenServiceImpl implements ExamenService {

    @Autowired
    private ExamenRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    @Override
    public ExamenDTO solicitarExamen(ExamenDTO dto) {
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

        // 3. Crear el Examen
        Examen examen = Examen.builder()
                .pacienteId(paciente.getId())
                .medicoId(medico.getId())
                .tipoExamen(dto.getTipoExamen())
                .fechaSolicitud(LocalDateTime.now())
                .resultado("Resultado pendiente de análisis.")
                .estado("SOLICITADO")
                .build();

        Examen guardado = repository.save(examen);

        return construirDTO(guardado, paciente, medico);
    }

    @Override
    public ExamenDTO registrarResultado(Long id, String resultado) {
        Examen examen = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El Examen con ID " + id + " no existe."));
        
        examen.setResultado(resultado);
        examen.setEstado("COMPLETADO");
        Examen guardado = repository.save(examen);

        return completarExamenConFeign(guardado);
    }

    @Override
    public List<ExamenDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::completarExamenConFeign)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamenDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(this::completarExamenConFeign)
                .collect(Collectors.toList());
    }

    // Auxiliar para rellenar datos usando Feign
    private ExamenDTO completarExamenConFeign(Examen examen) {
        PacienteDTO paciente = null;
        MedicoDTO medico = null;

        try { paciente = pacienteClient.obtenerPacientePorId(examen.getPacienteId()); } catch (Exception ignored) {}
        try { medico = medicoClient.obtenerMedicoPorId(examen.getMedicoId()); } catch (Exception ignored) {}

        return construirDTO(examen, paciente, medico);
    }

    // Auxiliar de mapeo
    private ExamenDTO construirDTO(Examen examen, PacienteDTO paciente, MedicoDTO medico) {
        return ExamenDTO.builder()
                .id(examen.getId())
                .pacienteId(examen.getPacienteId())
                .medicoId(examen.getMedicoId())
                .tipoExamen(examen.getTipoExamen())
                .fechaSolicitud(examen.getFechaSolicitud())
                .resultado(examen.getResultado())
                .estado(examen.getEstado())
                .paciente(paciente)
                .medico(medico)
                .build();
    }
}