package com.kinexus.back.controller.empresas;

import com.kinexus.back.dto.empresas.CreateSesionTrabajadorDTO;
import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import com.kinexus.back.service.empresas.SesionTrabajadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.kinexus.back.model.empresas.SesionTrabajadorId;

@RestController
@RequestMapping("/api/empresas/sesiones-trabajador")
@Tag(name = "Sesiones Trabajador - Empresa", description = "API para la gestión de sesiones de trabajador de empresa")
public class SesionTrabajadorController {
    private final SesionTrabajadorService sesionTrabajadorService;

    public SesionTrabajadorController(SesionTrabajadorService sesionTrabajadorService) {
        this.sesionTrabajadorService = sesionTrabajadorService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las sesiones de trabajador", description = "Retorna una lista con todas las sesiones de trabajador registradas.")
    public ResponseEntity<List<SesionTrabajadorEntity>> getAllSesionesTrabajador() {
        List<SesionTrabajadorEntity> sesiones = sesionTrabajadorService.getAllSesionesTrabajador();
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/{sesionId}/{usuarioEmpresaId}")
    @Operation(summary = "Obtener una sesión de trabajador por ID", description = "Retorna una sesión de trabajador específica basada en su ID compuesto.")
    public ResponseEntity<SesionTrabajadorEntity> getSesionTrabajadorById(@PathVariable UUID sesionId, @PathVariable UUID usuarioEmpresaId) {
        try {
            SesionTrabajadorId id = new SesionTrabajadorId(sesionId, usuarioEmpresaId);
            SesionTrabajadorEntity sesion = sesionTrabajadorService.getSesionTrabajadorById(id);
            return ResponseEntity.ok(sesion);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear una sesión de trabajador", description = "Registra una nueva sesión de trabajador en la base de datos.")
    public ResponseEntity<SesionTrabajadorEntity> createSesionTrabajador(@RequestBody CreateSesionTrabajadorDTO dto) {
        SesionTrabajadorEntity created = sesionTrabajadorService.createSesionTrabajador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{sesionId}/{usuarioEmpresaId}")
    @Operation(summary = "Actualizar parcialmente una sesión de trabajador", description = "Actualiza parcialmente los datos de una sesión de trabajador basada en su ID compuesto.")
    public ResponseEntity<SesionTrabajadorEntity> updateSesionTrabajador(@PathVariable UUID sesionId, @PathVariable UUID usuarioEmpresaId, @RequestBody CreateSesionTrabajadorDTO dto) {
        try {
            SesionTrabajadorId id = new SesionTrabajadorId(sesionId, usuarioEmpresaId);
            SesionTrabajadorEntity updated = sesionTrabajadorService.updateSesionTrabajador(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{sesionId}/{usuarioEmpresaId}")
    @Operation(summary = "Eliminar una sesión de trabajador", description = "Elimina una sesión de trabajador específica basada en su ID compuesto.")
    public ResponseEntity<String> deleteSesionTrabajador(@PathVariable UUID sesionId, @PathVariable UUID usuarioEmpresaId) {
        SesionTrabajadorId id = new SesionTrabajadorId(sesionId, usuarioEmpresaId);
        sesionTrabajadorService.deleteSesionTrabajador(id);
        return ResponseEntity.ok("Sesión de trabajador eliminada correctamente");
    }
}
