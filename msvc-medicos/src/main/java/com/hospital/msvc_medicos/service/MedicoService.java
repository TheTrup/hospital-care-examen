package com.hospital.msvc_medicos.service;

import com.hospital.msvc_medicos.dto.MedicoDTO;
import java.util.List;

public interface MedicoService {

    List<MedicoDTO> obtenerTodos();
    MedicoDTO obtenerPorId(Long id);
    MedicoDTO crear(MedicoDTO dto);

}
