package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreateContenidoMedicoDTO;
import com.kinexus.back.model.pacientes.ContenidoMedicoEntity;
import com.kinexus.back.service.pacientes.ContenidoMedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/contenidos-medicos")
@Tag(name = "Contenidos Médicos - Usuarios", description = "API para la gestión de contenidos médicos")
public class ContenidoMedicoController {
    private final ContenidoMedicoService contenidoMedicoService;

    public ContenidoMedicoController(ContenidoMedicoService contenidoMedicoService) {
        this.contenidoMedicoService = contenidoMedicoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los contenidos médicos", description = "Retorna una lista con todos los contenidos médicos registrados.")
    public ResponseEntity<List<ContenidoMedicoEntity>> getAllContenidosMedicos() {
        List<ContenidoMedicoEntity> contenidos = contenidoMedicoService.getAllContenidosMedicos();
        return ResponseEntity.ok(contenidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un contenido médico por ID", description = "Retorna un contenido médico específico basado en su ID.")
    public ResponseEntity<ContenidoMedicoEntity> getContenidoMedicoById(@PathVariable UUID id) {
        try {
            ContenidoMedicoEntity entity = contenidoMedicoService.getContenidoMedicoById(id);
            return ResponseEntity.ok(entity);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un contenido médico", description = "Registra un nuevo contenido médico en la base de datos.")
    public ResponseEntity<ContenidoMedicoEntity> createContenidoMedico(@RequestBody CreateContenidoMedicoDTO dto) {
        ContenidoMedicoEntity created = contenidoMedicoService.createContenidoMedico(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un contenido médico", description = "Actualiza parcialmente los datos de un contenido médico basado en su ID.")
    public ResponseEntity<ContenidoMedicoEntity> updateContenidoMedico(@PathVariable UUID id, @RequestBody CreateContenidoMedicoDTO dto) {
        try {
            ContenidoMedicoEntity updated = contenidoMedicoService.updateContenidoMedico(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un contenido médico", description = "Elimina un contenido médico específico basado en su ID.")
    public ResponseEntity<String> deleteContenidoMedico(@PathVariable UUID id) {
        contenidoMedicoService.deleteContenidoMedico(id);
        return ResponseEntity.ok("Contenido médico eliminado correctamente");
    }
}
