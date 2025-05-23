package com.kinexus.back.controller;

import com.kinexus.back.dto.CreateUserDTO;
import com.kinexus.back.model.UserEntity;
import com.kinexus.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Retorna un usuario específico basado en su ID.")
    public UserEntity getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @Operation(summary = "Crear un usuario", description = "Registra un nuevo usuario en la base de datos.")
    public UserEntity createUser(@RequestBody CreateUserDTO dto) {
        UserEntity user = UserEntity.builder()
                .nombre(dto.nombre)
                .email(dto.email)
                .password(dto.password)
                .tipoUsuario(dto.tipoUsuario)
                .creadoEn(LocalDateTime.now()) // aseguramos fecha de creación
                .build();
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario específico basado en su ID.")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un usuario", description = "Actualiza parcialmente los datos de un usuario basado en su ID.")
    public UserEntity updateUser(@PathVariable UUID id, @RequestBody CreateUserDTO dto) {
        return userService.updateUser(id, dto);
    }
}