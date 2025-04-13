package com.kinexus.back.controller;

import com.kinexus.back.dto.AuthRequest;
import com.kinexus.back.dto.AuthResponse;
import com.kinexus.back.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario registrado y devuelve un token JWT."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        System.out.println(new BCryptPasswordEncoder().matches("admin123", "$2a$10$K2Z0exPtjFECHFRjGmLQo.zKYuV8MjS1Tf8NS6y7AZZlVoHRYxdE2"));
        return ResponseEntity.ok(authService.login(request));
    }

}
