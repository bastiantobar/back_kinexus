package com.kinexus.back.controller;

import com.kinexus.back.dto.CreateCitaDTO;
import com.kinexus.back.model.CitaEntity;
import com.kinexus.back.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public List<CitaEntity> getAllCitas() {
        return citaService.getAllCitas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una cita por ID", description = "Retorna una cita específica basada en su ID.")
    public CitaEntity getCitaById(@PathVariable UUID id) {
        return citaService.getCitaById(id);
    }

    @PostMapping
    @Operation(summary = "Crear una cita", description = "Registra una nueva cita en la base de datos.")
    public CitaEntity createCita(@RequestBody CreateCitaDTO dto) {
        return citaService.createCita(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una cita", description = "Actualiza parcialmente los datos de una cita basada en su ID.")
    public CitaEntity updateCita(@PathVariable UUID id, @RequestBody CreateCitaDTO dto) {
        return citaService.updateCita(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cita", description = "Elimina una cita específica basada en su ID.")
    public void deleteCita(@PathVariable UUID id) {
        citaService.deleteCita(id);
    }
}
