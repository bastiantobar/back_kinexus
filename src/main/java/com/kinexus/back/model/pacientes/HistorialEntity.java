package com.kinexus.back.model.pacientes;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "historiales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Relación con pagos
    @OneToMany(cascade = CascadeType.ALL)
    private List<PagoEntity> pagos;

    // Info básica paciente (puedes ajustar según tu modelo de paciente)
    private String infoBasicaPaciente;

    // Lista de planes asociados al historial del paciente.
    // Usamos @ElementCollection porque PlanResumen es un objeto de valor, no una entidad independiente.
    @ElementCollection
    @CollectionTable(name = "historiales_planes", joinColumns = @JoinColumn(name = "historial_id"))
    private List<PlanResumen> planes;

    /**
     * PlanResumen representa un resumen de un plan asociado al historial.
     *
     * - @Embeddable indica que este objeto se almacena como parte de la entidad padre (HistorialEntity),
     *   no como una tabla independiente.
     * - No requiere @Entity ni @Id.
     */
    @Embeddable
    @Data
    public static class PlanResumen {
        private String fecha;
        private String tituloPlan;
        private String diagnostico;
        private String informeAlta;
    }
}
