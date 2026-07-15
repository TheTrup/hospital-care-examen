package com.hospital.msvc_fichas.service;

import com.hospital.msvc_fichas.dto.FichaDTO;
import java.util.List;

public interface FichaService {

    FichaDTO registrarFicha(FichaDTO dto);
    List<FichaDTO> obtenerTodas();
    List<FichaDTO> obtenerPorPaciente(Long pacienteId);

}
