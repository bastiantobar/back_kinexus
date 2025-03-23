package com.kinexus.back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Solicitud de inicio de sesión")
public class LoginRequest {
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;
}
