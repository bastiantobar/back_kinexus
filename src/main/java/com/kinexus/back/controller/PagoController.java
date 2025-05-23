package com.kinexus.back.controller;

import com.kinexus.back.dto.CreatePagoDTO;
import com.kinexus.back.model.PagoEntity;
import com.kinexus.back.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "API para la gestión de pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Retorna una lista con todos los pagos registrados.")
    public List<PagoEntity> getAllPagos() {
        return pagoService.getAllPagos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pago por ID", description = "Retorna un pago específico basado en su ID.")
    public PagoEntity getPagoById(@PathVariable UUID id) {
        return pagoService.getPagoById(id);
    }

    @PostMapping
    @Operation(summary = "Crear un pago", description = "Registra un nuevo pago en la base de datos.")
    public PagoEntity createPago(@RequestBody CreatePagoDTO dto) {
        return pagoService.createPago(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un pago", description = "Actualiza parcialmente los datos de un pago basado en su ID.")
    public PagoEntity updatePago(@PathVariable UUID id, @RequestBody CreatePagoDTO dto) {
        return pagoService.updatePago(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago específico basado en su ID.")
    public void deletePago(@PathVariable UUID id) {
        pagoService.deletePago(id);
    }
}
