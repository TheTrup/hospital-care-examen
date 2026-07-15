package com.hospital.msvc_especialidades.service;

import com.hospital.msvc_especialidades.dto.EspecialidadDTO;
import com.hospital.msvc_especialidades.model.Especialidad;
import com.hospital.msvc_especialidades.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository repository;

    @Override
    public EspecialidadDTO guardar(EspecialidadDTO dto) {
        // Validar duplicados de especialidad
        repository.findByNombreIgnoreCase(dto.getNombre())
                .ifPresent(e -> {
                    throw new RuntimeException("Error: La especialidad '" + dto.getNombre() + "' ya está registrada.");
                });

        Especialidad especialidad = Especialidad.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        Especialidad guardada = repository.save(especialidad);
        return construirDTO(guardada);
    }

    @Override
    public List<EspecialidadDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::construirDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EspecialidadDTO obtenerPorId(Long id) {
        Especialidad especialidad = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Especialidad con ID " + id + " no encontrada."));
        return construirDTO(especialidad);
    }

    // Auxiliar de mapeo Entity -> DTO
    private EspecialidadDTO construirDTO(Especialidad especialidad) {
        return EspecialidadDTO.builder()
                .id(especialidad.getId())
                .nombre(especialidad.getNombre())
                .descripcion(especialidad.getDescripcion())
                .build();
    }
}
