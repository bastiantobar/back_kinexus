package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "empresas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;
    private String ciudad;
    private String personaACargo;
    private String telefono;
    private String email;
    private String direccionCasaMatriz;
    private String descripcion;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<PlanEmpresaEntity> planes;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<SucursalEntity> sucursales;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<PagoEmpresaEntity> pagos;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<HistorialEmpresaEntity> historiales;
}
