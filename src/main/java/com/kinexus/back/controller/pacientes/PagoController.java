package com.kinexus.back.controller.pacientes;

import com.kinexus.back.dto.pacientes.CreatePagoDTO;
import com.kinexus.back.model.pacientes.PagoEntity;
import com.kinexus.back.service.pacientes.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos - Usuarios", description = "API para la gestión de pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Retorna una lista con todos los pagos registrados.")
    public ResponseEntity<List<PagoEntity>> getAllPagos() {
        List<PagoEntity> pagos = pagoService.getAllPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pago por ID", description = "Retorna un pago específico basado en su ID.")
    public ResponseEntity<PagoEntity> getPagoById(@PathVariable UUID id) {
        try {
            PagoEntity pago = pagoService.getPagoById(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un pago", description = "Registra un nuevo pago en la base de datos.")
    public ResponseEntity<PagoEntity> createPago(@RequestBody CreatePagoDTO dto) {
        PagoEntity created = pagoService.createPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un pago", description = "Actualiza parcialmente los datos de un pago basado en su ID.")
    public ResponseEntity<PagoEntity> updatePago(@PathVariable UUID id, @RequestBody CreatePagoDTO dto) {
        try {
            PagoEntity updated = pagoService.updatePago(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago específico basado en su ID.")
    public ResponseEntity<String> deletePago(@PathVariable UUID id) {
        pagoService.deletePago(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}
