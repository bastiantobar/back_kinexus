package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreateEmpresaDTO;
import com.kinexus.back.model.empresas.EmpresaEntity;
import com.kinexus.back.service.empresas.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "API para la gestión de empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las empresas", description = "Retorna una lista con todas las empresas registradas.")
    public ResponseEntity<List<EmpresaEntity>> getAllEmpresas() {
        List<EmpresaEntity> empresas = empresaService.getAllEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una empresa por ID", description = "Retorna una empresa específica basada en su ID.")
    public ResponseEntity<EmpresaEntity> getEmpresaById(@PathVariable UUID id) {
        try {
            EmpresaEntity empresa = empresaService.getEmpresaById(id);
            return ResponseEntity.ok(empresa);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear una empresa", description = "Registra una nueva empresa en la base de datos.")
    public ResponseEntity<EmpresaEntity> createEmpresa(@RequestBody CreateEmpresaDTO dto) {
        EmpresaEntity created = empresaService.createEmpresa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una empresa", description = "Actualiza parcialmente los datos de una empresa basada en su ID.")
    public ResponseEntity<EmpresaEntity> updateEmpresa(@PathVariable UUID id, @RequestBody CreateEmpresaDTO dto) {
        try {
            EmpresaEntity updated = empresaService.updateEmpresa(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa", description = "Elimina una empresa específica basada en su ID.")
    public ResponseEntity<String> deleteEmpresa(@PathVariable UUID id) {
        empresaService.deleteEmpresa(id);
        return ResponseEntity.ok("Empresa eliminada correctamente");
    }
}
