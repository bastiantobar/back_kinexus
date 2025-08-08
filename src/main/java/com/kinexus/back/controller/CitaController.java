package com.kinexus.back.controller;

import com.kinexus.back.dto.CreateCitaDTO;
import com.kinexus.back.model.CitaEntity;
import com.kinexus.back.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "API para la gestión de citas")
public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las citas", description = "Retorna una lista con todas las citas registradas.")
    public ResponseEntity<List<CitaEntity>> getAllCitas() {
        List<CitaEntity> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una cita por ID", description = "Retorna una cita específica basada en su ID.")
    public ResponseEntity<CitaEntity> getCitaById(@PathVariable UUID id) {
        try {
            CitaEntity cita = citaService.getCitaById(id);
            return ResponseEntity.ok(cita);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear una cita", description = "Registra una nueva cita en la base de datos.")
    public ResponseEntity<CitaEntity> createCita(@RequestBody CreateCitaDTO dto) {
        CitaEntity created = citaService.createCita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una cita", description = "Actualiza parcialmente los datos de una cita basada en su ID.")
    public ResponseEntity<CitaEntity> updateCita(@PathVariable UUID id, @RequestBody CreateCitaDTO dto) {
        try {
            CitaEntity updated = citaService.updateCita(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cita", description = "Elimina una cita específica basada en su ID.")
    public ResponseEntity<String> deleteCita(@PathVariable UUID id) {
        citaService.deleteCita(id);
        return ResponseEntity.ok("Cita eliminada correctamente");
    }
}
