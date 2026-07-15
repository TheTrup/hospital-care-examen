package com.hospital.msvc_recetas.dto;

import com.hospital.msvc_recetas.dto.external.MedicoDTO;
import com.hospital.msvc_recetas.dto.external.PacienteDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaEmision;
    private String medicamentos;
    private String estado;

    // Se rellenarán dinámicamente llamando a los otros microservicios con Feign
    private PacienteDTO paciente;
    private MedicoDTO medico;
}