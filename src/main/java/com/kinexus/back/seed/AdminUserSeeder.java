package com.kinexus.back.seed;

import com.kinexus.back.model.UserEntity;
import com.kinexus.back.model.UserType;
import com.kinexus.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "admin1@example.com";

        if (userRepository.findByEmail(email).isEmpty()) {
            UserEntity user = UserEntity.builder()
                    .email(email)
                    .nombre("Admin1")
                    .password(passwordEncoder.encode("admin123"))
                    .tipoUsuario(UserType.admin)
                    .creadoEn(LocalDateTime.now())
                    .build();

            userRepository.save(user);
            System.out.println("✅ Usuario admin@example.com creado con password admin123");
        }
    }
}
