package com.hospital.msvc_recetas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Long medicoId;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false, length = 1000)
    private String medicamentos; // Ejemplo: "Amoxicilina 500mg, 1 comprimido cada 8 horas por 7 días"

    @Column(nullable = false)
    private String estado; // Ejemplo: ACTIVA, VENCIDA, ENTREGADA
}
