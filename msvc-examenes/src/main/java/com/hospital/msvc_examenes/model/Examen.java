package com.hospital.msvc_examenes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "examenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Long medicoId;

    @Column(nullable = false, length = 100)
    private String tipoExamen; // Ejemplo: Hemograma, Radiografía de Tórax, PCR

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(length = 1000)
    private String resultado; // Ejemplo: "Glóbulos blancos normales", "Sin hallazgos patológicos"

    @Column(nullable = false, length = 50)
    private String estado; // Ejemplo: SOLICITADO, COMPLETADO, ENTREGADO
}