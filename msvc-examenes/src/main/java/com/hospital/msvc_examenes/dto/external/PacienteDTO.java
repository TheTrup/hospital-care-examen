package com.hospital.msvc_examenes.dto.external;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}