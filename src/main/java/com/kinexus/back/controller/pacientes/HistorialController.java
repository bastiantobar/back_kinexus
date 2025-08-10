package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreateHistorialDTO;
import com.kinexus.back.model.pacientes.HistorialEntity;
import com.kinexus.back.service.pacientes.HistorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pacientes/historiales")
@Tag(name = "Historiales - Usuarios", description = "API para la gestión de historiales clínicos")
public class HistorialController {
    private final HistorialService historialService;
    public HistorialController(HistorialService historialService) { this.historialService = historialService; }

    @GetMapping
    @Operation(summary = "Obtener todos los historiales", description = "Retorna una lista con todos los historiales registrados.")
    public ResponseEntity<List<HistorialEntity>> getAll() { return ResponseEntity.ok(historialService.getAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un historial por ID", description = "Retorna un historial específico basado en su ID.")
    public ResponseEntity<HistorialEntity> getById(@PathVariable UUID id) { return ResponseEntity.ok(historialService.getById(id)); }

    @PostMapping
    @Operation(summary = "Crear un historial", description = "Registra un nuevo historial en la base de datos.")
    public ResponseEntity<HistorialEntity> create(@RequestBody CreateHistorialDTO dto) {
        HistorialEntity historial = HistorialEntity.builder()
            .infoBasicaPaciente(dto.infoBasicaPaciente)
            // pagos y planes pueden mapearse si lo necesitas
            .build();
        return ResponseEntity.status(201).body(historialService.create(historial));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un historial", description = "Actualiza parcialmente los datos de un historial basado en su ID.")
    public ResponseEntity<HistorialEntity> update(@PathVariable UUID id, @RequestBody HistorialEntity historial) { return ResponseEntity.ok(historialService.update(id, historial)); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un historial", description = "Elimina un historial específico basado en su ID.")
    public ResponseEntity<String> delete(@PathVariable UUID id) { historialService.delete(id); return ResponseEntity.ok("Historial eliminado correctamente"); }
}
