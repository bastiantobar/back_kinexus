package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreatePlanDTO;
import com.kinexus.back.model.pacientes.PlanEntity;
import com.kinexus.back.service.pacientes.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pacientes/planes")
@Tag(name = "Planes - Usuarios", description = "API para la gestión de planes de tratamiento")
public class PlanController {
    private final PlanService planService;
    public PlanController(PlanService planService) { this.planService = planService; }

    @GetMapping
    @Operation(summary = "Obtener todos los planes", description = "Retorna una lista con todos los planes registrados.")
    public ResponseEntity<List<PlanEntity>> getAll() { return ResponseEntity.ok(planService.getAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un plan por ID", description = "Retorna un plan específico basado en su ID.")
    public ResponseEntity<PlanEntity> getById(@PathVariable UUID id) { return ResponseEntity.ok(planService.getById(id)); }

    @PostMapping
    @Operation(summary = "Crear un plan", description = "Registra un nuevo plan en la base de datos.")
    public ResponseEntity<PlanEntity> create(@RequestBody CreatePlanDTO dto) {
        PlanEntity plan = PlanEntity.builder()
            .asistencia(dto.asistencia)
            .numeroSesiones(dto.numeroSesiones)
            .infoPlan(dto.infoPlan)
            .evaluacionInicial(dto.evaluacionInicial)
            .evaluacionFinal(dto.evaluacionFinal)
            .build();
        return ResponseEntity.status(201).body(planService.create(plan));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un plan", description = "Actualiza parcialmente los datos de un plan basado en su ID.")
    public ResponseEntity<PlanEntity> update(@PathVariable UUID id, @RequestBody PlanEntity plan) { return ResponseEntity.ok(planService.update(id, plan)); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un plan", description = "Elimina un plan específico basado en su ID.")
    public ResponseEntity<String> delete(@PathVariable UUID id) { planService.delete(id); return ResponseEntity.ok("Plan eliminado correctamente"); }
}
