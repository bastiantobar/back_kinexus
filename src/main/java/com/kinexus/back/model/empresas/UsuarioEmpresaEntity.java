package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "usuarios_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    private String nombre;
    private Integer edad;
    private String genero;
    private Date fechaNacimiento;
    private String cargo;
    private String discapacidad;
}
