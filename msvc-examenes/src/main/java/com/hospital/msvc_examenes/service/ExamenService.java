package com.hospital.msvc_examenes.service;

import com.hospital.msvc_examenes.dto.ExamenDTO;
import java.util.List;

public interface ExamenService {
    ExamenDTO solicitarExamen(ExamenDTO dto);
    ExamenDTO registrarResultado(Long id, String resultado);
    List<ExamenDTO> obtenerTodos();
    List<ExamenDTO> obtenerPorPaciente(Long pacienteId);
}