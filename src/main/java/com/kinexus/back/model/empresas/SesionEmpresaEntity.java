package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "sesiones_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SesionEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanEmpresaEntity plan;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    private LocalDateTime fechaHora;
    private String estado;
    private String descripcionClinica;
    private Double asistencia;

    @OneToMany(mappedBy = "sesion", cascade = CascadeType.ALL)
    private List<SesionTrabajadorEntity> asistencias;
}
