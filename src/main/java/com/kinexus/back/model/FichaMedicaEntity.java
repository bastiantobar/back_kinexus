package com.kinexus.back.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "fichas_medicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa una ficha medica de un paciente")
public class FichaMedicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único de la ficha medica", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "ID único del paciente", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID pacienteId;

    @Column(nullable = false)
    @Schema(description = "Descripción de la ficha medica del paciente", example = "Ficha medica - Tratamiento lesion LCA")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de creación de la ficha medica", example = "2024-03-23T12:34:56")
    private LocalDateTime creadoEn;
}
