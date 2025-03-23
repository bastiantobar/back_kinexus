package com.kinexus.back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta de autenticación con el token JWT")
public class AuthResponse {
    @Schema(description = "Token JWT generado tras el inicio de sesión")
    private String token;

    @Schema(description = "Mensaje de respuesta")
    private String message;
}
