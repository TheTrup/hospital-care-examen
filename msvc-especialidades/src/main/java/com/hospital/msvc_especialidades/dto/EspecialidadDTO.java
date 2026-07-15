package com.hospital.msvc_especialidades.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadDTO {
    private Long id;

    @NotBlank(message = "El nombre de la especialidad es obligatorio.")
    private String nombre;

    private String descripcion;
}