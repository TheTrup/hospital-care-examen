package com.hospital.msvc_citas.service;

import com.hospital.msvc_citas.dto.CitaDTO;
import java.util.List;

public interface CitaService {
    CitaDTO agendarCita(CitaDTO dto);
    List<CitaDTO> obtenerTodas();
}