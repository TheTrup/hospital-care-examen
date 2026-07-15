package com.hospital.msvc_medicos.service;

import com.hospital.msvc_medicos.dto.MedicoDTO;
import com.hospital.msvc_medicos.model.Medico;
import com.hospital.msvc_medicos.repository.MedicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MedicoServiceImpl implements MedicoService{

    @Autowired
    private MedicoRepository repository;

    @Override
    public List<MedicoDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(m -> MedicoDTO.builder()
                        .id(m.getId()).rut(m.getRut()).nombre(m.getNombre())
                        .apellido(m.getApellido()).especialidad(m.getEspecialidad()).build())
                .collect(Collectors.toList());
    }

    @Override
    public MedicoDTO obtenerPorId(Long id) {
        Medico medico = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
        return MedicoDTO.builder()
                .id(medico.getId()).rut(medico.getRut()).nombre(medico.getNombre())
                .apellido(medico.getApellido()).especialidad(medico.getEspecialidad()).build();
    }

    @Override
    public MedicoDTO crear(MedicoDTO dto) {
        if(repository.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("El médico con este RUT ya existe.");
        }
        Medico medico = Medico.builder()
                .rut(dto.getRut()).nombre(dto.getNombre()).apellido(dto.getApellido())
                .especialidad(dto.getEspecialidad()).build();
        Medico guardado = repository.save(medico);
        dto.setId(guardado.getId());
        return dto;
    }
}
