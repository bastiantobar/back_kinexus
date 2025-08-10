package com.kinexus.back.model.pacientes;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "asistencia")
    private Boolean asistencia;

    @Column(name = "cancelado")
    private Boolean cancelado;

    @Column(name = "evolucion_clinica")
    private String evolucionClinica;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanEntity plan;
}
