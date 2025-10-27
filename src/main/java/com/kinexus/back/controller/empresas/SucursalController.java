package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreateSucursalDTO;
import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.service.empresas.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas/sucursales")
@Tag(name = "Sucursales - Empresa", description = "API para la gestión de sucursales de empresa")
public class SucursalController {
    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }


    @GetMapping("/plan/{planId}")
    @Operation(summary = "Obtener todas las sucursales por planId", description = "Retorna una lista con todas las sucursales asociadas a un plan.")
    public ResponseEntity<List<SucursalEntity>> getSucursalesByPlanId(@PathVariable UUID planId) {
        List<SucursalEntity> sucursales = sucursalService.getSucursalesByPlanId(planId);
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtener todas las sucursales por empresaId", description = "Retorna una lista con todas las sucursales asociadas a una empresa (a través de sus planes).")
    public ResponseEntity<List<SucursalEntity>> getSucursalesByEmpresaId(@PathVariable UUID empresaId) {
        List<SucursalEntity> sucursales = sucursalService.getSucursalesByEmpresaId(empresaId);
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sucursal por ID", description = "Retorna una sucursal específica basada en su ID.")
    public ResponseEntity<SucursalEntity> getSucursalById(@PathVariable UUID id) {
        try {
            SucursalEntity sucursal = sucursalService.getSucursalById(id);
            return ResponseEntity.ok(sucursal);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear una sucursal", description = "Registra una nueva sucursal en la base de datos.")
    public ResponseEntity<SucursalEntity> createSucursal(@RequestBody CreateSucursalDTO dto) {
        SucursalEntity created = sucursalService.createSucursal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una sucursal", description = "Actualiza parcialmente los datos de una sucursal basada en su ID.")
    public ResponseEntity<SucursalEntity> updateSucursal(@PathVariable UUID id, @RequestBody CreateSucursalDTO dto) {
        try {
            SucursalEntity updated = sucursalService.updateSucursal(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal específica basada en su ID.")
    public ResponseEntity<String> deleteSucursal(@PathVariable UUID id) {
        sucursalService.deleteSucursal(id);
        return ResponseEntity.ok("Sucursal eliminada correctamente");
    }
}
