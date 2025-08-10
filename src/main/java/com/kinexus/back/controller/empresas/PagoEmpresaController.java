package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreatePagoEmpresaDTO;
import com.kinexus.back.model.empresas.PagoEmpresaEntity;
import com.kinexus.back.service.empresas.PagoEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas/pagos")
@Tag(name = "Pagos - Empresa", description = "API para la gestión de pagos de empresa")
public class PagoEmpresaController {
    private final PagoEmpresaService pagoEmpresaService;

    public PagoEmpresaController(PagoEmpresaService pagoEmpresaService) {
        this.pagoEmpresaService = pagoEmpresaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos de empresa", description = "Retorna una lista con todos los pagos de empresa registrados.")
    public ResponseEntity<List<PagoEmpresaEntity>> getAllPagosEmpresa() {
        List<PagoEmpresaEntity> pagos = pagoEmpresaService.getAllPagosEmpresa();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pago de empresa por ID", description = "Retorna un pago de empresa específico basado en su ID.")
    public ResponseEntity<PagoEmpresaEntity> getPagoEmpresaById(@PathVariable UUID id) {
        try {
            PagoEmpresaEntity pago = pagoEmpresaService.getPagoEmpresaById(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un pago de empresa", description = "Registra un nuevo pago de empresa en la base de datos.")
    public ResponseEntity<PagoEmpresaEntity> createPagoEmpresa(@RequestBody CreatePagoEmpresaDTO dto) {
        PagoEmpresaEntity created = pagoEmpresaService.createPagoEmpresa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un pago de empresa", description = "Actualiza parcialmente los datos de un pago de empresa basado en su ID.")
    public ResponseEntity<PagoEmpresaEntity> updatePagoEmpresa(@PathVariable UUID id, @RequestBody CreatePagoEmpresaDTO dto) {
        try {
            PagoEmpresaEntity updated = pagoEmpresaService.updatePagoEmpresa(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago de empresa", description = "Elimina un pago de empresa específico basado en su ID.")
    public ResponseEntity<String> deletePagoEmpresa(@PathVariable UUID id) {
        pagoEmpresaService.deletePagoEmpresa(id);
        return ResponseEntity.ok("Pago de empresa eliminado correctamente");
    }
}
