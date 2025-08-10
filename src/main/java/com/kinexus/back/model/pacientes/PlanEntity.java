package com.kinexus.back.model.pacientes;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "planes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "asistencia")
    private Integer asistencia;

    @Column(name = "numero_sesiones")
    private Integer numeroSesiones;

    @Column(name = "info_plan")
    private String infoPlan;

    @Column(name = "evaluacion_inicial")
    private String evaluacionInicial;

    @Column(name = "evaluacion_final")
    private String evaluacionFinal;

    // Relación con sesiones
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<SesionEntity> sesiones;

    // Relación con pagos
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PagoEntity> pagos;
}
