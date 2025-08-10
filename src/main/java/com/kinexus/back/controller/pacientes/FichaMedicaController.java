package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreateFichaMedicaDTO;
import com.kinexus.back.model.pacientes.FichaMedicaEntity;
import com.kinexus.back.service.pacientes.FichaMedicaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fichas-medicas")
@Tag(name = "Fichas Médicas - Usuarios", description = "API para la gestión de fichas médicas")
public class FichaMedicaController {

    private final FichaMedicaService fichaMedicaService;

    public FichaMedicaController(FichaMedicaService fichaMedicaService) {
        this.fichaMedicaService = fichaMedicaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las fichas médicas", description = "Retorna una lista con todas las fichas médicas registradas.")
    public ResponseEntity<List<FichaMedicaEntity>> getAllFichasMedicas() {
        List<FichaMedicaEntity> fichas = fichaMedicaService.getAllFichasMedicas();
        return ResponseEntity.ok(fichas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una ficha médica por ID", description = "Retorna una ficha médica específica basada en su ID.")
    public ResponseEntity<FichaMedicaEntity> getFichaMedicaById(@PathVariable UUID id) {
        try {
            FichaMedicaEntity entity = fichaMedicaService.getFichaMedicaById(id);
            return ResponseEntity.ok(entity);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear una ficha médica", description = "Registra una nueva ficha médica en la base de datos.")
    public ResponseEntity<FichaMedicaEntity> createFichaMedica(@RequestBody CreateFichaMedicaDTO fichaMedicaDTO) {
        // Validación de existencia de paciente
        if (!fichaMedicaService.getUserRepository().existsById(fichaMedicaDTO.pacienteId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El paciente no existe");
        }
        FichaMedicaEntity entity = FichaMedicaEntity.builder()
                .pacienteId(fichaMedicaDTO.pacienteId)
                .descripcion(fichaMedicaDTO.descripcion)
                .creadoEn(LocalDateTime.now()) // aseguramos fecha de creación
                .build();
        FichaMedicaEntity created = fichaMedicaService.createFichaMedica(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ficha médica", description = "Elimina una ficha médica específica basada en su ID.")
    public ResponseEntity<String> deleteFichaMedica(@PathVariable UUID id) {
        fichaMedicaService.deleteFichaMedica(id);
        return ResponseEntity.ok("Ficha médica eliminada correctamente");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una ficha médica", description = "Actualiza parcialmente los datos de una ficha médica basada en su ID.")
    public ResponseEntity<FichaMedicaEntity> updateFichaMedica(@PathVariable UUID id, @RequestBody CreateFichaMedicaDTO fichaMedicaDTO) {
        try {
            FichaMedicaEntity updated = fichaMedicaService.updateFichaMedica(id, fichaMedicaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
