package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreateSesionDTO;
import com.kinexus.back.model.pacientes.SesionEntity;
import com.kinexus.back.service.pacientes.SesionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pacientes/sesiones")
@Tag(name = "Sesiones - Usuarios", description = "API para la gestión de sesiones de tratamiento")
public class SesionController {
    private final SesionService sesionService;
    public SesionController(SesionService sesionService) { this.sesionService = sesionService; }

    @GetMapping
    @Operation(summary = "Obtener todas las sesiones", description = "Retorna una lista con todas las sesiones registradas.")
    public ResponseEntity<List<SesionEntity>> getAll() { return ResponseEntity.ok(sesionService.getAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sesión por ID", description = "Retorna una sesión específica basada en su ID.")
    public ResponseEntity<SesionEntity> getById(@PathVariable UUID id) { return ResponseEntity.ok(sesionService.getById(id)); }

    @PostMapping
    @Operation(summary = "Crear una sesión", description = "Registra una nueva sesión en la base de datos.")
    public ResponseEntity<SesionEntity> create(@RequestBody CreateSesionDTO dto) {
        SesionEntity sesion = SesionEntity.builder()
            .fechaHora(dto.fechaHora)
            .asistencia(dto.asistencia)
            .cancelado(dto.cancelado)
            .evolucionClinica(dto.evolucionClinica)
            .build();
        return ResponseEntity.status(201).body(sesionService.create(sesion));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una sesión", description = "Actualiza parcialmente los datos de una sesión basada en su ID.")
    public ResponseEntity<SesionEntity> update(@PathVariable UUID id, @RequestBody SesionEntity sesion) { return ResponseEntity.ok(sesionService.update(id, sesion)); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sesión", description = "Elimina una sesión específica basada en su ID.")
    public ResponseEntity<String> delete(@PathVariable UUID id) { sesionService.delete(id); return ResponseEntity.ok("Sesión eliminada correctamente"); }
}
