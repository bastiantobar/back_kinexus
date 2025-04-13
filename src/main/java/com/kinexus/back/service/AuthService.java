package com.kinexus.back.service;

import com.kinexus.back.dto.AuthRequest;
import com.kinexus.back.dto.AuthResponse;
import com.kinexus.back.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        // Lanza BadCredentialsException si falla
        System.out.println("🔑 Intentando login con:");
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password ingresado: " + request.getPassword());
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String username = auth.getName(); // o getPrincipal().getUsername() si casteas
        String token = jwtUtil.generateToken(username);

        return new AuthResponse(token);
    }
}

