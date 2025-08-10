package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.PagoEmpresaEntity;
import com.kinexus.back.dto.empresas.CreatePagoEmpresaDTO;
import com.kinexus.back.repository.empresas.PagoEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PagoEmpresaService {
    private final PagoEmpresaRepository pagoEmpresaRepository;

    public PagoEmpresaService(PagoEmpresaRepository pagoEmpresaRepository) {
        this.pagoEmpresaRepository = pagoEmpresaRepository;
    }

    public List<PagoEmpresaEntity> getAllPagosEmpresa() {
        return pagoEmpresaRepository.findAll();
    }

    public PagoEmpresaEntity getPagoEmpresaById(UUID id) {
        return pagoEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PagoEmpresa no encontrado"));
    }

    public PagoEmpresaEntity createPagoEmpresa(CreatePagoEmpresaDTO dto) {
        PagoEmpresaEntity pago = PagoEmpresaEntity.builder()
                .monto(dto.monto)
                .fechaPago(dto.fechaPago)
                .metodoPago(dto.metodoPago)
                .estadoPago(dto.estadoPago)
                .build();
        return pagoEmpresaRepository.save(pago);
    }

    public PagoEmpresaEntity updatePagoEmpresa(UUID id, CreatePagoEmpresaDTO dto) {
        PagoEmpresaEntity pago = getPagoEmpresaById(id);
        pago.setMonto(dto.monto);
        pago.setFechaPago(dto.fechaPago);
        pago.setMetodoPago(dto.metodoPago);
        pago.setEstadoPago(dto.estadoPago);
        return pagoEmpresaRepository.save(pago);
    }

    public void deletePagoEmpresa(UUID id) {
        if (!pagoEmpresaRepository.existsById(id)) {
            throw new RuntimeException("PagoEmpresa no encontrado");
        }
        pagoEmpresaRepository.deleteById(id);
    }
}
