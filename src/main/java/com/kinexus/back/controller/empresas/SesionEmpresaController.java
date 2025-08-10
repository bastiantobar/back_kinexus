package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreateSesionEmpresaDTO;
import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import com.kinexus.back.service.empresas.SesionEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas/sesiones")
@Tag(name = "Sesiones - Empresa", description = "API para la gestión de sesiones de empresa")
public class SesionEmpresaController {
    private final SesionEmpresaService sesionEmpresaService;

    public SesionEmpresaController(SesionEmpresaService sesionEmpresaService) {
        this.sesionEmpresaService = sesionEmpresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las sesiones de empresa", description = "Retorna una lista con todas las sesiones de empresa registradas.")
    public ResponseEntity<List<SesionEmpresaEntity>> getAllSesionesEmpresa() {
        List<SesionEmpresaEntity> sesiones = sesionEmpresaService.getAllSesionesEmpresa();
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sesión de empresa por ID", description = "Retorna una sesión de empresa específica basada en su ID.")
    public ResponseEntity<SesionEmpresaEntity> getSesionEmpresaById(@PathVariable UUID id) {
        try {
            SesionEmpresaEntity sesion = sesionEmpresaService.getSesionEmpresaById(id);
            return ResponseEntity.ok(sesion);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear una sesión de empresa", description = "Registra una nueva sesión de empresa en la base de datos.")
    public ResponseEntity<SesionEmpresaEntity> createSesionEmpresa(@RequestBody CreateSesionEmpresaDTO dto) {
        SesionEmpresaEntity created = sesionEmpresaService.createSesionEmpresa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una sesión de empresa", description = "Actualiza parcialmente los datos de una sesión de empresa basada en su ID.")
    public ResponseEntity<SesionEmpresaEntity> updateSesionEmpresa(@PathVariable UUID id, @RequestBody CreateSesionEmpresaDTO dto) {
        try {
            SesionEmpresaEntity updated = sesionEmpresaService.updateSesionEmpresa(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sesión de empresa", description = "Elimina una sesión de empresa específica basada en su ID.")
    public ResponseEntity<String> deleteSesionEmpresa(@PathVariable UUID id) {
        sesionEmpresaService.deleteSesionEmpresa(id);
        return ResponseEntity.ok("Sesión de empresa eliminada correctamente");
    }
}
