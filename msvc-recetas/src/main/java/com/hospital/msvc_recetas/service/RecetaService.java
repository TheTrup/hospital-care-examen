package com.hospital.msvc_recetas.service;

import com.hospital.msvc_recetas.dto.RecetaDTO;
import java.util.List;

public interface RecetaService {
    RecetaDTO crearReceta(RecetaDTO dto);
    List<RecetaDTO> obtenerTodas();
    List<RecetaDTO> obtenerPorPaciente(Long pacienteId);
}