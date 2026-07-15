package com.hospital.msvc_especialidades.service;

import com.hospital.msvc_especialidades.dto.EspecialidadDTO;
import java.util.List;

public interface EspecialidadService {
    EspecialidadDTO guardar(EspecialidadDTO dto);
    List<EspecialidadDTO> obtenerTodas();
    EspecialidadDTO obtenerPorId(Long id);
}
