package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.ALWAYS)
@Table(name = "sesion_trabajador")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SesionTrabajadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sesion_id")
    @JsonIgnore
    private SesionEmpresaEntity sesion;

    @ManyToOne
    @JoinColumn(name = "usuario_empresa_id")
    @JsonIgnore
    private UsuarioEmpresaEntity usuarioEmpresa;

    private Boolean asistencia;
}


