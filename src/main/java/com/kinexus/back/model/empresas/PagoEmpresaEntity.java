package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "pagos_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PagoEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnore
    private EmpresaEntity empresa;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanEmpresaEntity plan;

    private Double monto;
    private String metodoPago;
    private String estadoPago;
    private LocalDateTime fechaPago;
}
