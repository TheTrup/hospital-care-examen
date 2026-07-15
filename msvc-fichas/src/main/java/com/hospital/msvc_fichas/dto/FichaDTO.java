package com.hospital.msvc_fichas.dto;

import com.hospital.msvc_fichas.dto.external.MedicoDTO;
import com.hospital.msvc_fichas.dto.external.PacienteDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FichaDTO {

    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaRegistro;
    private String diagnostico;
    private String tratamiento;

  
    private PacienteDTO paciente;
    private MedicoDTO medico;

}
