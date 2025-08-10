
package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;
import com.kinexus.back.model.empresas.SesionTrabajadorId;

@Entity
@Table(name = "sesion_trabajador")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SesionTrabajadorEntity {
    @EmbeddedId
    private SesionTrabajadorId id;

    @ManyToOne
    @MapsId("sesionId")
    @JoinColumn(name = "sesion_id")
    private SesionEmpresaEntity sesion;

    @ManyToOne
    @MapsId("usuarioEmpresaId")
    @JoinColumn(name = "usuario_empresa_id")
    private UsuarioEmpresaEntity usuarioEmpresa;

    private Boolean asistencia;
}


