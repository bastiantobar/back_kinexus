package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreateHistorialEmpresaDTO;
import com.kinexus.back.model.empresas.HistorialEmpresaEntity;
import com.kinexus.back.service.empresas.HistorialEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas/historial")
@Tag(name = "Historial - Empresa", description = "API para la gestión del historial de empresa")
public class HistorialEmpresaController {
    private final HistorialEmpresaService historialEmpresaService;

    public HistorialEmpresaController(HistorialEmpresaService historialEmpresaService) {
        this.historialEmpresaService = historialEmpresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todo el historial de empresa", description = "Retorna una lista con todo el historial de empresa registrado.")
    public ResponseEntity<List<HistorialEmpresaEntity>> getAllHistorialEmpresa() {
        List<HistorialEmpresaEntity> historial = historialEmpresaService.getAllHistorialEmpresa();
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un historial de empresa por ID", description = "Retorna un historial de empresa específico basado en su ID.")
    public ResponseEntity<HistorialEmpresaEntity> getHistorialEmpresaById(@PathVariable UUID id) {
        try {
            HistorialEmpresaEntity historial = historialEmpresaService.getHistorialEmpresaById(id);
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un historial de empresa", description = "Registra un nuevo historial de empresa en la base de datos.")
    public ResponseEntity<HistorialEmpresaEntity> createHistorialEmpresa(@RequestBody CreateHistorialEmpresaDTO dto) {
        HistorialEmpresaEntity created = historialEmpresaService.createHistorialEmpresa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un historial de empresa", description = "Actualiza parcialmente los datos de un historial de empresa basado en su ID.")
    public ResponseEntity<HistorialEmpresaEntity> updateHistorialEmpresa(@PathVariable UUID id, @RequestBody CreateHistorialEmpresaDTO dto) {
        try {
            HistorialEmpresaEntity updated = historialEmpresaService.updateHistorialEmpresa(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un historial de empresa", description = "Elimina un historial de empresa específico basado en su ID.")
    public ResponseEntity<String> deleteHistorialEmpresa(@PathVariable UUID id) {
        historialEmpresaService.deleteHistorialEmpresa(id);
        return ResponseEntity.ok("Historial de empresa eliminado correctamente");
    }
}
