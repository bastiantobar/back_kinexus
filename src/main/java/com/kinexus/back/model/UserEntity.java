package com.kinexus.back.model;

import com.kinexus.back.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa a un usuario del sistema")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(nullable = false, length = 255)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(nullable = false, unique = true, length = 255)
    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@email.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contraseña cifrada del usuario")
    private String password;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de usuario", example = "ADMIN", allowableValues = {"ADMIN", "PACIENTE", "PACIENTE_EMPRESA"})
    private UserType tipoUsuario;

    @Column(name = "creado_en", updatable = false, nullable = false)
    @Schema(description = "Fecha y hora de creación del usuario", example = "2024-03-23T12:34:56")
    private LocalDateTime creadoEn = LocalDateTime.now();
}