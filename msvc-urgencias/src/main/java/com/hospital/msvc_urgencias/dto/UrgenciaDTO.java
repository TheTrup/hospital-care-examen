package com.hospital.msvc_urgencias.dto;

import com.hospital.msvc_urgencias.dto.external.MedicoDTO;
import com.hospital.msvc_urgencias.dto.external.PacienteDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrgenciaDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String triage;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaEgreso;
    private String motivoConsulta;
    private String estado;

    // Se completarán dinámicamente mediante las llamadas OpenFeign
    private PacienteDTO paciente;
    private MedicoDTO medico;
}
