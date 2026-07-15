package com.hospital.msvc_pacientes.service;

import com.hospital.msvc_pacientes.dto.PacienteDTO;
import java.util.List;

public interface PacienteService {

    List<PacienteDTO> obtenerTodos();
    PacienteDTO obtenerPorId(Long id);
    PacienteDTO obtenerPorRut(String rut);
    PacienteDTO crear(PacienteDTO dto);
    PacienteDTO actualizar(Long id, PacienteDTO dto);
    void eliminar(Long id);

}
