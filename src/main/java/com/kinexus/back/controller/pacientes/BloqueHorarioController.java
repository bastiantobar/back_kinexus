package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreateBloqueHorarioDTO;
import com.kinexus.back.model.pacientes.BloqueHorarioEntity;
import com.kinexus.back.service.pacientes.BloqueHorarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pacientes/bloques-horarios")
@Tag(name = "Bloques Horario - Usuarios", description = "API para la gestión de bloques de horario")
public class BloqueHorarioController {
    private final BloqueHorarioService bloqueHorarioService;
    public BloqueHorarioController(BloqueHorarioService bloqueHorarioService) { this.bloqueHorarioService = bloqueHorarioService; }

    @GetMapping
    @Operation(summary = "Obtener todos los bloques de horario", description = "Retorna una lista con todos los bloques de horario registrados.")
    public ResponseEntity<List<BloqueHorarioEntity>> getAll() { return ResponseEntity.ok(bloqueHorarioService.getAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un bloque de horario por ID", description = "Retorna un bloque de horario específico basado en su ID.")
    public ResponseEntity<BloqueHorarioEntity> getById(@PathVariable UUID id) { return ResponseEntity.ok(bloqueHorarioService.getById(id)); }

    @PostMapping
    @Operation(summary = "Crear un bloque de horario", description = "Registra un nuevo bloque de horario en la base de datos.")
    public ResponseEntity<BloqueHorarioEntity> create(@RequestBody CreateBloqueHorarioDTO dto) {
        BloqueHorarioEntity bloque = BloqueHorarioEntity.builder()
            .descripcion(dto.descripcion)
            .build();
        return ResponseEntity.status(201).body(bloqueHorarioService.create(bloque));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un bloque de horario", description = "Actualiza parcialmente los datos de un bloque de horario basado en su ID.")
    public ResponseEntity<BloqueHorarioEntity> update(@PathVariable UUID id, @RequestBody BloqueHorarioEntity bloque) { return ResponseEntity.ok(bloqueHorarioService.update(id, bloque)); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un bloque de horario", description = "Elimina un bloque de horario específico basado en su ID.")
    public ResponseEntity<String> delete(@PathVariable UUID id) { bloqueHorarioService.delete(id); return ResponseEntity.ok("Bloque horario eliminado correctamente"); }
}
