package com.hospital.msvc_hospitalizacion.dto;

import com.hospital.msvc_hospitalizacion.dto.external.MedicoDTO;
import com.hospital.msvc_hospitalizacion.dto.external.PacienteDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalizacionDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaAlta;
    private String sala;
    private String cama;
    private String motivoIngreso;
    private String estado;

    // Se completarán dinámicamente llamando a los otros microservicios con Feign
    private PacienteDTO paciente;
    private MedicoDTO medico;
}