package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreateUsuarioEmpresaDTO;
import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import com.kinexus.back.service.empresas.UsuarioEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas/usuarios")
@Tag(name = "Usuarios - Empresa", description = "API para la gestión de usuarios de empresa")
public class UsuarioEmpresaController {
    private final UsuarioEmpresaService usuarioEmpresaService;

    public UsuarioEmpresaController(UsuarioEmpresaService usuarioEmpresaService) {
        this.usuarioEmpresaService = usuarioEmpresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios de empresa", description = "Retorna una lista con todos los usuarios de empresa registrados.")
    public ResponseEntity<List<UsuarioEmpresaEntity>> getAllUsuariosEmpresa() {
        List<UsuarioEmpresaEntity> usuarios = usuarioEmpresaService.getAllUsuariosEmpresa();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario de empresa por ID", description = "Retorna un usuario de empresa específico basado en su ID.")
    public ResponseEntity<UsuarioEmpresaEntity> getUsuarioEmpresaById(@PathVariable UUID id) {
        try {
            UsuarioEmpresaEntity usuario = usuarioEmpresaService.getUsuarioEmpresaById(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un usuario de empresa", description = "Registra un nuevo usuario de empresa en la base de datos.")
    public ResponseEntity<UsuarioEmpresaEntity> createUsuarioEmpresa(@RequestBody CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity created = usuarioEmpresaService.createUsuarioEmpresa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un usuario de empresa", description = "Actualiza parcialmente los datos de un usuario de empresa basado en su ID.")
    public ResponseEntity<UsuarioEmpresaEntity> updateUsuarioEmpresa(@PathVariable UUID id, @RequestBody CreateUsuarioEmpresaDTO dto) {
        try {
            UsuarioEmpresaEntity updated = usuarioEmpresaService.updateUsuarioEmpresa(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario de empresa", description = "Elimina un usuario de empresa específico basado en su ID.")
    public ResponseEntity<String> deleteUsuarioEmpresa(@PathVariable UUID id) {
        usuarioEmpresaService.deleteUsuarioEmpresa(id);
        return ResponseEntity.ok("Usuario de empresa eliminado correctamente");
    }
}
