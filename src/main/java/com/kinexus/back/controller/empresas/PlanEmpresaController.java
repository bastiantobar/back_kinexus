package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreatePlanEmpresaDTO;
import com.kinexus.back.model.empresas.PlanEmpresaEntity;
import com.kinexus.back.service.empresas.PlanEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas/planes")
@Tag(name = "Planes - Empresa", description = "API para la gestión de planes de empresa")
public class PlanEmpresaController {
    private final PlanEmpresaService planEmpresaService;

    public PlanEmpresaController(PlanEmpresaService planEmpresaService) {
        this.planEmpresaService = planEmpresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los planes de empresa", description = "Retorna una lista con todos los planes de empresa registrados.")
    public ResponseEntity<List<PlanEmpresaEntity>> getAllPlanes() {
        List<PlanEmpresaEntity> planes = planEmpresaService.getAllPlanes();
        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un plan de empresa por ID", description = "Retorna un plan de empresa específico basado en su ID.")
    public ResponseEntity<PlanEmpresaEntity> getPlanById(@PathVariable UUID id) {
        try {
            PlanEmpresaEntity plan = planEmpresaService.getPlanById(id);
            return ResponseEntity.ok(plan);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un plan de empresa", description = "Registra un nuevo plan de empresa en la base de datos.")
    public ResponseEntity<PlanEmpresaEntity> createPlan(@RequestBody CreatePlanEmpresaDTO dto) {
        PlanEmpresaEntity created = planEmpresaService.createPlan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un plan de empresa", description = "Actualiza parcialmente los datos de un plan de empresa basado en su ID.")
    public ResponseEntity<PlanEmpresaEntity> updatePlan(@PathVariable UUID id, @RequestBody CreatePlanEmpresaDTO dto) {
        try {
            PlanEmpresaEntity updated = planEmpresaService.updatePlan(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un plan de empresa", description = "Elimina un plan de empresa específico basado en su ID.")
    public ResponseEntity<String> deletePlan(@PathVariable UUID id) {
        planEmpresaService.deletePlan(id);
        return ResponseEntity.ok("Plan de empresa eliminado correctamente");
    }
}
