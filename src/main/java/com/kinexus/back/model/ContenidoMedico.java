package com.kinexus.back.model;

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
public class ContenidoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID fichaId;

    @Column(nullable = false, length = 10)
    private String tipoContenido;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDateTime creadoEn;
}
