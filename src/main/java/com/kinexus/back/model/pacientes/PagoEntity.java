package com.kinexus.back.model.pacientes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un pago")
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único del pago", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que realiza el pago", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID usuarioId;

    @Column(nullable = false)
    @Schema(description = "Monto del pago en pesos chilenos", example = "10000")
    private Integer monto;

    @Column(nullable = false, length = 20)
    @Schema(description = "Método de pago", example = "efectivo", allowableValues = {"efectivo", "tarjeta", "transferencia"})
    private String metodoPago;

    @Column(nullable = false, length = 20)
    @Schema(description = "Estado del pago", example = "pendiente", allowableValues = {"pendiente", "completado", "fallido"})
    private String estadoPago;

    @Column(nullable = false)
    @Schema(description = "Fecha del pago", example = "2024-03-23T12:34:56")
    private LocalDateTime fechaPago;

    // Relación con PlanEntity (muchos pagos pueden pertenecer a un plan)
    @ManyToOne
    @JoinColumn(name = "plan_id")
    @Schema(description = "Plan asociado a este pago")
    private PlanEntity plan;
}
