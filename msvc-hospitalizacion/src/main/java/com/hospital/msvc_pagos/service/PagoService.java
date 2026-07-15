package com.hospital.msvc_pagos.service;

import com.hospital.msvc_pagos.dto.PagoDTO;
import java.util.List;

public interface PagoService {
    PagoDTO procesarPago(PagoDTO dto);
    List<PagoDTO> obtenerTodos();
    List<PagoDTO> obtenerPorPaciente(Long pacienteId);
}
