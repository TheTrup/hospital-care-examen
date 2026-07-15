package com.hospital.msvc_citas.dto.external;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String especialidad;
    
}
