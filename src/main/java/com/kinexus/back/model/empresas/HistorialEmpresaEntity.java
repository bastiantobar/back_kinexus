package com.kinexus.back.model.empresas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historiales_empresa")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistorialEmpresaEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaEntity empresa;

    private String tipo; // 'plan', 'sesion', 'pago', etc.
    private String descripcion;
    private LocalDateTime fecha;
}
