package com.kinexus.back.controller;

import com.kinexus.back.dto.CreateFichaMedicaDTO;
import com.kinexus.back.model.FichaMedicaEntity;
import com.kinexus.back.model.UserEntity;
import com.kinexus.back.service.FichaMedicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fichas-medicas")
@Tag(name = "Fichas Médicas", description = "API para la gestión de fichas médicas")
public class FichaMedicaController {

    private final FichaMedicaService fichaMedicaService;

    public FichaMedicaController(FichaMedicaService fichaMedicaService) {
        this.fichaMedicaService = fichaMedicaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las fichas médicas", description = "Retorna una lista con todas las fichas médicas registradas.")
    public List<FichaMedicaEntity> getAllFichasMedicas() {
        return fichaMedicaService.getAllFichasMedicas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una ficha médica por ID", description = "Retorna una ficha médica específica basada en su ID.")
    public FichaMedicaEntity getFichaMedicaById(@PathVariable UUID id) {
        return fichaMedicaService.getFichaMedicaById(id);
    }

    @PostMapping
    @Operation(summary = "Crear una ficha médica", description = "Registra una nueva ficha médica en la base de datos.")
    public FichaMedicaEntity createFichaMedica(@RequestBody CreateFichaMedicaDTO fichaMedicaDTO) {
        FichaMedicaEntity fichaMedica = FichaMedicaEntity.builder()
                .pacienteId(fichaMedicaDTO.pacienteId)
                .descripcion(fichaMedicaDTO.descripcion)
                .creadoEn(LocalDateTime.now()) // aseguramos fecha de creación
                .build();
        return fichaMedicaService.createFichaMedica(fichaMedica);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ficha médica", description = "Elimina una ficha médica específica basada en su ID.")
    public void deleteFichaMedica(@PathVariable UUID id) {
        fichaMedicaService.deleteFichaMedica(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una ficha médica", description = "Actualiza parcialmente los datos de una ficha médica basada en su ID.")
    public FichaMedicaEntity updateFichaMedica(@PathVariable UUID id, @RequestBody CreateFichaMedicaDTO fichaMedicaDTO) {
        return fichaMedicaService.updateFichaMedica(id, fichaMedicaDTO);
    }
}
