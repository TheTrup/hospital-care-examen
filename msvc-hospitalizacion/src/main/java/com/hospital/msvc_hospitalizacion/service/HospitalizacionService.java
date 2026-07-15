package com.hospital.msvc_hospitalizacion.service;

import com.hospital.msvc_hospitalizacion.dto.HospitalizacionDTO;
import java.util.List;

public interface HospitalizacionService {
    HospitalizacionDTO registrarIngreso(HospitalizacionDTO dto);
    HospitalizacionDTO darDeAlta(Long id);
    List<HospitalizacionDTO> obtenerTodas();
    List<HospitalizacionDTO> obtenerPorPaciente(Long pacienteId);
}