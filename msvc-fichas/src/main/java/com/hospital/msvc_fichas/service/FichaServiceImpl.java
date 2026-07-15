package com.hospital.msvc_fichas.service;

import com.hospital.msvc_fichas.client.MedicoClient;
import com.hospital.msvc_fichas.client.PacienteClient;
import com.hospital.msvc_fichas.dto.FichaDTO;
import com.hospital.msvc_fichas.dto.external.MedicoDTO;
import com.hospital.msvc_fichas.dto.external.PacienteDTO;
import com.hospital.msvc_fichas.model.FichaClinica;
import com.hospital.msvc_fichas.repository.FichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaServiceImpl implements FichaService {

    @Autowired
    private FichaRepository repository;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    @Override
    public FichaDTO registrarFicha(FichaDTO dto) {
        // 1. Validar si el Paciente existe vía Feign
        PacienteDTO paciente;
        try {
            paciente = pacienteClient.obtenerPacientePorId(dto.getPacienteId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Paciente con ID " + dto.getPacienteId() + " no existe.");
        }

        // 2. Validar si el Médico existe vía Feign
        MedicoDTO medico;
        try {
            medico = medicoClient.obtenerMedicoPorId(dto.getMedicoId());
        } catch (Exception e) {
            throw new RuntimeException("Error: El Médico con ID " + dto.getMedicoId() + " no existe.");
        }

        // 3. Registrar la ficha clínica
        FichaClinica ficha = FichaClinica.builder()
                .pacienteId(paciente.getId())
                .medicoId(medico.getId())
                .fechaRegistro(LocalDateTime.now()) // Registrar fecha actual del sistema
                .diagnostico(dto.getDiagnostico())
                .tratamiento(dto.getTratamiento())
                .build();

        FichaClinica guardada = repository.save(ficha);

        // 4. Retornar el DTO mapeado con la información remota embebida
        return construirDTO(guardada, paciente, medico);
    }

    @Override
    public List<FichaDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::completarFichaConFeign)
                .collect(Collectors.toList());
    }

    @Override
    public List<FichaDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(this::completarFichaConFeign)
                .collect(Collectors.toList());
    }

    // Método auxiliar para buscar por Feign y ensamblar el DTO
    private FichaDTO completarFichaConFeign(FichaClinica ficha) {
        PacienteDTO paciente = null;
        MedicoDTO medico = null;

        try { paciente = pacienteClient.obtenerPacientePorId(ficha.getPacienteId()); } catch (Exception ignored) {}
        try { medico = medicoClient.obtenerMedicoPorId(ficha.getMedicoId()); } catch (Exception ignored) {}

        return construirDTO(ficha, paciente, medico);
    }

    // Método auxiliar de mapeo final
    private FichaDTO construirDTO(FichaClinica ficha, PacienteDTO paciente, MedicoDTO medico) {
        return FichaDTO.builder()
                .id(ficha.getId())
                .pacienteId(ficha.getPacienteId())
                .medicoId(ficha.getMedicoId())
                .fechaRegistro(ficha.getFechaRegistro())
                .diagnostico(ficha.getDiagnostico())
                .tratamiento(ficha.getTratamiento())
                .paciente(paciente)
                .medico(medico)
                .build();
    }
}