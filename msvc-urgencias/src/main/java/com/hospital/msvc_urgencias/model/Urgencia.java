package com.hospital.msvc_urgencias.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urgencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Urgencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Long medicoId;

    @Column(nullable = false, length = 10)
    private String triage; // Categorización de gravedad: C1 (Crítico), C2, C3, C4, C5 (No urgente)

    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaEgreso;

    @Column(nullable = false, length = 1000)
    private String motivoConsulta;

    @Column(nullable = false, length = 50)
    private String estado; // Ejemplo: EN_ATENCION, DADO_DE_ALTA, DERIVADO_A_PISO
}