package com.kinexus.back.controller;

import com.kinexus.back.dto.CreateUserDTO;
import com.kinexus.back.model.UserEntity;
import com.kinexus.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista con todos los usuarios registrados.")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Retorna un usuario específico basado en su ID.")
    public ResponseEntity<UserEntity> getUserById(@PathVariable UUID id) {
        try {
            UserEntity user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un usuario", description = "Registra un nuevo usuario en la base de datos.")
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserDTO dto) {
        UserEntity user = UserEntity.builder()
                .nombre(dto.nombre)
                .email(dto.email)
                .password(dto.password)
                .tipoUsuario(dto.tipoUsuario)
                .creadoEn(LocalDateTime.now())
                .build();
        UserEntity created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario específico basado en su ID.")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un usuario", description = "Actualiza parcialmente los datos de un usuario basado en su ID.")
    public ResponseEntity<UserEntity> updateUser(@PathVariable UUID id, @RequestBody CreateUserDTO dto) {
        try {
            UserEntity updated = userService.updateUser(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}