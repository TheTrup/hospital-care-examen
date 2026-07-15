package com.hospital.msvc_pagos.dto;

import com.hospital.msvc_pagos.dto.external.PacienteDTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDTO {
    private Long id;
    private Long pacienteId;
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String estado;

    // Se completará dinámicamente mapeando con Feign
    private PacienteDTO paciente;
}
