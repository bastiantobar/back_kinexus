package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "sucursales")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SucursalEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnore
    private EmpresaEntity empresa;

    private Integer numeroTrabajadores;
    private String email;
    private String telefono;
    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private List<UsuarioEmpresaEntity> trabajadores;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private List<SesionEmpresaEntity> sesiones;
}
