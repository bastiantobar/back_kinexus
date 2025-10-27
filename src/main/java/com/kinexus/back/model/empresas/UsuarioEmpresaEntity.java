package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "usuarios_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @JsonIgnore
    private SucursalEntity sucursal;

    private String nombre;
    private String genero;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String cargo;
    private String discapacidad;
    // Relación con asistencias del trabajador
    @OneToMany(mappedBy = "usuarioEmpresa", cascade = CascadeType.ALL)
    private List<SesionTrabajadorEntity> asistencias;
}
