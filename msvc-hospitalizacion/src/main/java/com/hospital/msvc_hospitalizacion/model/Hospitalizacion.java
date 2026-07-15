package com.hospital.msvc_hospitalizacion.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospitalizaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospitalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Long medicoId;

    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaAlta;

    @Column(nullable = false, length = 50)
    private String sala; // Ejemplo: "Piso 3 - Sala A"

    @Column(nullable = false, length = 50)
    private String cama; // Ejemplo: "Cama 104"

    @Column(nullable = false, length = 1000)
    private String motivoIngreso;

    @Column(nullable = false, length = 50)
    private String estado; // Ejemplo: INGRESADO, DE_ALTA
}