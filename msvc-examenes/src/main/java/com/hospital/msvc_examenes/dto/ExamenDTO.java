package com.hospital.msvc_examenes.dto;

import com.hospital.msvc_examenes.dto.external.MedicoDTO;
import com.hospital.msvc_examenes.dto.external.PacienteDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String tipoExamen;
    private LocalDateTime fechaSolicitud;
    private String resultado;
    private String estado;

    // Se completarán dinámicamente llamando a los otros microservicios con Feign
    private PacienteDTO paciente;
    private MedicoDTO medico;
}
