package com.hospital.msvc_urgencias.service;

import com.hospital.msvc_urgencias.dto.UrgenciaDTO;
import java.util.List;

public interface UrgenciaService {
    UrgenciaDTO registrarIngresoUrgencia(UrgenciaDTO dto);
    UrgenciaDTO actualizarEstadoEgreso(Long id, String nuevoEstado);
    List<UrgenciaDTO> obtenerTodas();
    List<UrgenciaDTO> obtenerPorPaciente(Long pacienteId);
}
