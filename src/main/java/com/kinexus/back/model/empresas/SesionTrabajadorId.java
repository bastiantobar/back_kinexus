package com.kinexus.back.model.empresas;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SesionTrabajadorId implements Serializable {
    private UUID sesionId;
    private UUID usuarioEmpresaId;
}
