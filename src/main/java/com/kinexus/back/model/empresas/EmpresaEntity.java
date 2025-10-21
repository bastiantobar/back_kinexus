package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    // Relación con planes
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<PlanEmpresaEntity> planes;

    // Relación con historiales
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<HistorialEmpresaEntity> historiales;
}
