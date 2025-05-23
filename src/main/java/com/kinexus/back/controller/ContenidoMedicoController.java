package com.kinexus.back.controller;

import com.kinexus.back.dto.CreateContenidoMedicoDTO;
import com.kinexus.back.model.ContenidoMedicoEntity;
import com.kinexus.back.service.ContenidoMedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contenidos-medicos")
@Tag(name = "Contenidos Médicos", description = "API para la gestión de contenidos médicos")
public class ContenidoMedicoController {
    private final ContenidoMedicoService contenidoMedicoService;

    public ContenidoMedicoController(ContenidoMedicoService contenidoMedicoService) {
        this.contenidoMedicoService = contenidoMedicoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los contenidos médicos", description = "Retorna una lista con todos los contenidos médicos registrados.")
    public List<ContenidoMedicoEntity> getAllContenidosMedicos() {
        return contenidoMedicoService.getAllContenidosMedicos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un contenido médico por ID", description = "Retorna un contenido médico específico basado en su ID.")
    public ContenidoMedicoEntity getContenidoMedicoById(@PathVariable UUID id) {
        return contenidoMedicoService.getContenidoMedicoById(id);
    }

    @PostMapping
    @Operation(summary = "Crear un contenido médico", description = "Registra un nuevo contenido médico en la base de datos.")
    public ContenidoMedicoEntity createContenidoMedico(@RequestBody CreateContenidoMedicoDTO dto) {
        return contenidoMedicoService.createContenidoMedico(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un contenido médico", description = "Actualiza parcialmente los datos de un contenido médico basado en su ID.")
    public ContenidoMedicoEntity updateContenidoMedico(@PathVariable UUID id, @RequestBody CreateContenidoMedicoDTO dto) {
        return contenidoMedicoService.updateContenidoMedico(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un contenido médico", description = "Elimina un contenido médico específico basado en su ID.")
    public void deleteContenidoMedico(@PathVariable UUID id) {
        contenidoMedicoService.deleteContenidoMedico(id);
    }
}
