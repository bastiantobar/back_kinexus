package com.kinexus.back.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contenidos_medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un contenido médico (PDF/Video)")
public class ContenidoMedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único del contenido médico", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "ID de la ficha médica asociada", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID fichaId;

    @Column(nullable = false, length = 10)
    @Schema(description = "Tipo de contenido", example = "pdf", allowableValues = {"pdf", "video"})
    private String tipoContenido;

    @Column(nullable = false)
    @Schema(description = "URL del contenido médico")
    private String url;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación", example = "2024-03-23T12:34:56")
    private LocalDateTime creadoEn;
}
