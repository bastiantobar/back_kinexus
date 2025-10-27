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

@RestController
@RequestMapping("/api/empresas/sesiones-trabajador")
@Tag(name = "Sesiones Trabajador - Empresa", description = "API para la gestión de sesiones de trabajador de empresa")
public class SesionTrabajadorController {
    @GetMapping("/sesion-empresa/{sesionEmpresaId}")
    @Operation(summary = "Obtener todas las sesiones de trabajador por sesión de empresa", description = "Retorna una lista con todas las sesiones de trabajador asociadas a un sesionEmpresaId.")
    public ResponseEntity<List<SesionTrabajadorEntity>> getSesionesBySesionEmpresaId(@PathVariable UUID sesionEmpresaId) {
        List<SesionTrabajadorEntity> sesiones = sesionTrabajadorService.getSesionesBySesionEmpresaId(sesionEmpresaId);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/sucursal/{sucursalId}")
    @Operation(summary = "Obtener todas las sesiones de trabajador por sucursal", description = "Retorna una lista con todas las sesiones de trabajador asociadas a un sucursalId.")
    public ResponseEntity<List<SesionTrabajadorEntity>> getSesionesBySucursalId(@PathVariable UUID sucursalId) {
        List<SesionTrabajadorEntity> sesiones = sesionTrabajadorService.getSesionesBySucursalId(sucursalId);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/plan/{planId}")
    @Operation(summary = "Obtener todas las sesiones de trabajador por plan", description = "Retorna una lista con todas las sesiones de trabajador asociadas a un planId.")
    public ResponseEntity<List<SesionTrabajadorEntity>> getSesionesByPlanId(@PathVariable UUID planId) {
        List<SesionTrabajadorEntity> sesiones = sesionTrabajadorService.getSesionesByPlanId(planId);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtener todas las sesiones de trabajador por empresa", description = "Retorna una lista con todas las sesiones de trabajador asociadas a un empresaId.")
    public ResponseEntity<List<SesionTrabajadorEntity>> getSesionesByEmpresaId(@PathVariable UUID empresaId) {
        List<SesionTrabajadorEntity> sesiones = sesionTrabajadorService.getSesionesByEmpresaId(empresaId);
        return ResponseEntity.ok(sesiones);
    }
    private final SesionTrabajadorService sesionTrabajadorService;

    public SesionTrabajadorController(SesionTrabajadorService sesionTrabajadorService) {
        this.sesionTrabajadorService = sesionTrabajadorService;
    }



    @GetMapping("/usuario-empresa/{usuarioEmpresaId}")
    @Operation(summary = "Obtener todas las sesiones de un usuario de empresa", description = "Retorna una lista con todas las sesiones de trabajador asociadas a un usuarioEmpresaId.")
    public ResponseEntity<List<SesionTrabajadorEntity>> getSesionesByUsuarioEmpresaId(@PathVariable UUID usuarioEmpresaId) {
        List<SesionTrabajadorEntity> sesiones = sesionTrabajadorService.getSesionesByUsuarioEmpresaId(usuarioEmpresaId);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sesión de trabajador por ID", description = "Retorna una sesión de trabajador específica basada en su UUID.")
    public ResponseEntity<SesionTrabajadorEntity> getSesionTrabajadorById(@PathVariable UUID id) {
        try {
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

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una sesión de trabajador", description = "Actualiza parcialmente los datos de una sesión de trabajador basada en su UUID.")
    public ResponseEntity<SesionTrabajadorEntity> updateSesionTrabajador(@PathVariable UUID id, @RequestBody CreateSesionTrabajadorDTO dto) {
        try {
            SesionTrabajadorEntity updated = sesionTrabajadorService.updateSesionTrabajador(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/{id}/marcar")
    @Operation(summary = "Marcar asistencia y descripción clínica", description = "Permite marcar la asistencia y actualizar la descripción clínica de una sesión de trabajador por su ID.")
    public ResponseEntity<SesionTrabajadorEntity> marcarAsistenciaYDescripcion(@PathVariable UUID id, @RequestBody CreateSesionTrabajadorDTO dto) {
        try {
            SesionTrabajadorEntity updated = sesionTrabajadorService.updateSesionTrabajador(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sesión de trabajador", description = "Elimina una sesión de trabajador específica basada en su UUID.")
    public ResponseEntity<String> deleteSesionTrabajador(@PathVariable UUID id) {
        sesionTrabajadorService.deleteSesionTrabajador(id);
        return ResponseEntity.ok("Sesión de trabajador eliminada correctamente");
    }
}
