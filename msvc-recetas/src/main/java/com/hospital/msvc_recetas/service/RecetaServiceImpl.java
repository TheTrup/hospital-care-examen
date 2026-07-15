package com.hospital.msvc_recetas.service;

import com.hospital.msvc_recetas.client.MedicoClient;
import com.hospital.msvc_recetas.client.PacienteClient;
import com.hospital.msvc_recetas.dto.RecetaDTO;
import com.hospital.msvc_recetas.dto.external.MedicoDTO;
import com.hospital.msvc_recetas.dto.external.PacienteDTO;
import com.hospital.msvc_recetas.model.Receta;
import com.hospital.msvc_recetas.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    @Override
    public RecetaDTO crearReceta(RecetaDTO dto) {
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

        // 3. Crear y guardar la Receta
        Receta receta = Receta.builder()
                .pacienteId(paciente.getId())
                .medicoId(medico.getId())
                .fechaEmision(LocalDateTime.now())
                .medicamentos(dto.getMedicamentos())
                .estado("ACTIVA") // Estado por defecto
                .build();

        Receta guardada = repository.save(receta);

        // 4. Retornar el DTO con toda la información resuelta
        return construirDTO(guardada, paciente, medico);
    }

    @Override
    public List<RecetaDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::completarRecetaConFeign)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecetaDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(this::completarRecetaConFeign)
                .collect(Collectors.toList());
    }

    // Auxiliar para rellenar los datos usando Feign
    private RecetaDTO completarRecetaConFeign(Receta receta) {
        PacienteDTO paciente = null;
        MedicoDTO medico = null;

        try { paciente = pacienteClient.obtenerPacientePorId(receta.getPacienteId()); } catch (Exception ignored) {}
        try { medico = medicoClient.obtenerMedicoPorId(receta.getMedicoId()); } catch (Exception ignored) {}

        return construirDTO(receta, paciente, medico);
    }

    // Auxiliar de mapeo
    private RecetaDTO construirDTO(Receta receta, PacienteDTO paciente, MedicoDTO medico) {
        return RecetaDTO.builder()
                .id(receta.getId())
                .pacienteId(receta.getPacienteId())
                .medicoId(receta.getMedicoId())
                .fechaEmision(receta.getFechaEmision())
                .medicamentos(receta.getMedicamentos())
                .estado(receta.getEstado())
                .paciente(paciente)
                .medico(medico)
                .build();
    }
}