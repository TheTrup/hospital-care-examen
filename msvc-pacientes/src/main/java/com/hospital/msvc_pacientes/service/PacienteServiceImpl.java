package com.hospital.msvc_pacientes.service;

import com.hospital.msvc_pacientes.dto.PacienteDTO;
import com.hospital.msvc_pacientes.model.Paciente;
import com.hospital.msvc_pacientes.repository.PacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j 
@Service
public class PacienteServiceImpl implements PacienteService{

    @Autowired
    private PacienteRepository repository;

    @Override
    public List<PacienteDTO> obtenerTodos() {
        log.info("Obteniendo la lista de todos los pacientes");
        return repository.findAll().stream()
                .map(this::convertirAEntityDto)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteDTO obtenerPorId(Long id) {
        log.info("Buscando paciente por ID: {}", id);
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Paciente con ID {} no encontrado", id);
                    return new RuntimeException("Paciente no encontrado con el ID especificado.");
                });
        return convertirAEntityDto(paciente);
    }

    @Override
    public PacienteDTO obtenerPorRut(String rut) {
        log.info("Buscando paciente por RUT: {}", rut);
        Paciente paciente = repository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con el RUT especificado."));
        return convertirAEntityDto(paciente);
    }

    @Override
    public PacienteDTO crear(PacienteDTO dto) {
        log.info("Registrando un nuevo paciente con RUT: {}", dto.getRut());
        
        // Validación de negocio: Evitar duplicación de RUT (IE 2.2.1)
        if (repository.findByRut(dto.getRut()).isPresent()) {
            log.warn("Intento fallido de registro: El RUT {} ya existe", dto.getRut());
            throw new RuntimeException("El RUT ingresado ya está registrado en el sistema.");
        }

        // Validación de negocio en vivo: Validador de RUT (Dígito verificador)
        if (!validarRutChileno(dto.getRut())) {
            log.warn("Intento de registro con RUT inválido: {}", dto.getRut());
            throw new RuntimeException("El RUT ingresado no es un RUT chileno válido.");
        }

        Paciente paciente = convertirADtoEntity(dto);
        Paciente guardado = repository.save(paciente);
        log.info("Paciente creado exitosamente con ID: {}", guardado.getId());
        return convertirAEntityDto(guardado);
    }

    @Override
    public PacienteDTO actualizar(Long id, PacienteDTO dto) {
        log.info("Actualizando paciente con ID: {}", id);
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar. Paciente no encontrado."));

        // Impedir que cambien el RUT a uno existente de otro paciente
        if (!paciente.getRut().equals(dto.getRut()) && repository.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("El nuevo RUT ya pertenece a otro paciente.");
        }

        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefono(dto.getTelefono());

        Paciente actualizado = repository.save(paciente);
        return convertirAEntityDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando paciente con ID: {}", id);
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar. Paciente no encontrado."));
        repository.delete(paciente);
        log.info("Paciente con ID: {} eliminado correctamente", id);
    }

    // --- Métodos Auxiliares de Mapeo (Para evitar exponer Entidades) ---
    private PacienteDTO convertirAEntityDto(Paciente p) {
        return PacienteDTO.builder()
                .id(p.getId())
                .rut(p.getRut())
                .nombre(p.getNombre())
                .apellido(p.getApellido())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .build();
    }

    private Paciente convertirADtoEntity(PacienteDTO d) {
        return Paciente.builder()
                .id(d.getId())
                .rut(d.getRut())
                .nombre(d.getNombre())
                .apellido(d.getApellido())
                .email(d.getEmail())
                .telefono(d.getTelefono())
                .build();
    }

    // --- Algoritmo de validación de RUT Chileno (Módulo 11) ---
    private boolean validarRutChileno(String rutCompleto) {
        try {
            String[] partes = rutCompleto.split("-");
            int rut = Integer.parseInt(partes[0]);
            char dv = partes[1].toUpperCase().charAt(0);

            int m = 0, s = 1;
            for (; rut != 0; rut /= 10) {
                s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            }
            char dvCalculado = (char) (s != 0 ? s + 47 : 75);
            return dv == dvCalculado;
        } catch (Exception e) {
            return false;
        }
    }

}
