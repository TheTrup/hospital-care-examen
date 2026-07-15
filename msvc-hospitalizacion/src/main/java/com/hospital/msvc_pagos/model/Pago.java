package com.hospital.msvc_pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 50)
    private String metodoPago; // Ejemplo: "Efectivo", "Tarjeta de Crédito", "Transferencia"

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false, length = 50)
    private String estado; // Ejemplo: PROCESADO, ANULADO
}