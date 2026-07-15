package com.hospital.msvc_citas.dto;


import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {

    private Long id;

    @NotNull(message = "El ID del paciente es obligatorio.")
    private Long pacienteId;

    @NotNull(message = "El ID del médico es obligatorio.")
    private Long medicoId;

    @NotNull(message = "La fecha y hora de la cita es obligatoria.")
    @Future(message = "La fecha de la cita debe ser en el futuro.")
    private LocalDateTime fechaHora;

    private String estado;

}
