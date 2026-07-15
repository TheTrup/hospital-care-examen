package com.hospital.msvc_pacientes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PacienteDTO {

    private Long id;

    @NotBlank(message = "El RUT es obligatorio.")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]{1}$", message = "El formato del RUT debe ser sin puntos y con guion (ej: 12345678-9).")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres.")
    private String apellido;

    @Email(message = "Debe proporcionar una dirección de correo válida.")
    @NotBlank(message = "El correo es obligatorio.")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "El teléfono debe ser un formato válido (ej: +56912345678).")
    private String telefono;

}

