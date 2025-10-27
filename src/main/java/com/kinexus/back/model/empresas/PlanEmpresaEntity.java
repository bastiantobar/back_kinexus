package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "planes_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Relación con empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnore
    private EmpresaEntity empresa;

    private String nombre;
    private String descripcion;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;
    private Double valor;
    private Integer numeroSesiones;

    // Relación con sucursales asociadas al plan
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<SucursalEntity> sucursales;

    // Relación con pagos asociados al plan
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PagoEmpresaEntity> pagos;

    // Las sesiones estarán asociadas a cada sucursal, no al plan.
}
