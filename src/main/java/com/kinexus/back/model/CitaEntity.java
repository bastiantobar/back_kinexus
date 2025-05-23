package com.kinexus.back.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa una cita médica")
public class CitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único de la cita", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = true)
    @Schema(description = "ID del paciente (puede ser null)", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID pacienteId;

    @Column(nullable = false)
    @Schema(description = "ID del administrador que crea la cita", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID adminId;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de la cita", example = "2024-03-23T12:34:56")
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 20)
    @Schema(description = "Estado de la cita", example = "disponible", allowableValues = {"disponible", "reservada", "cancelada"})
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación de la cita", example = "2024-03-23T12:34:56")
    private LocalDateTime creadoEn;
}
