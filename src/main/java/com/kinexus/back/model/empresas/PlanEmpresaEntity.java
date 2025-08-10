package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "planes_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaEntity empresa;

    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaTermino;
    private Double valor;
    private Integer numeroSesiones;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<SesionEmpresaEntity> sesiones;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PagoEmpresaEntity> pagos;
}
