package com.kinexus.back.service.pacientes;

import com.kinexus.back.dto.pacientes.CreatePagoDTO;
import com.kinexus.back.model.pacientes.PagoEntity;
import com.kinexus.back.repository.UserRepository;
import com.kinexus.back.repository.pacientes.PagoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final UserRepository userRepository;

    public PagoService(PagoRepository pagoRepository, UserRepository userRepository) {
        this.pagoRepository = pagoRepository;
        this.userRepository = userRepository;
    }

    public List<PagoEntity> getAllPagos() {
        return pagoRepository.findAll();
    }

    public PagoEntity getPagoById(UUID id) {
        return pagoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
    }

    public PagoEntity createPago(CreatePagoDTO dto) {
        if (!userRepository.existsById(dto.usuarioId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe");
        }
        PagoEntity entity = PagoEntity.builder()
                .usuarioId(dto.usuarioId)
                .monto(dto.monto)
                .metodoPago(dto.metodoPago)
                .estadoPago(dto.estadoPago != null ? dto.estadoPago : "pendiente")
                .fechaPago(LocalDateTime.now())
                .build();
        return pagoRepository.save(entity);
    }

    public PagoEntity updatePago(UUID id, CreatePagoDTO dto) {
        PagoEntity entity = pagoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        if (dto.usuarioId != null) {
            if (!userRepository.existsById(dto.usuarioId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe");
            }
            entity.setUsuarioId(dto.usuarioId);
        }
        if (dto.monto != null) entity.setMonto(dto.monto);
        if (dto.metodoPago != null) entity.setMetodoPago(dto.metodoPago);
        if (dto.estadoPago != null) entity.setEstadoPago(dto.estadoPago);
        return pagoRepository.save(entity);
    }

    public void deletePago(UUID id) {
        if (!pagoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado");
        }
        pagoRepository.deleteById(id);
    }
}
