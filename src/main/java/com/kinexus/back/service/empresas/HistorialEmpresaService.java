package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.HistorialEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateHistorialEmpresaDTO;
import com.kinexus.back.repository.empresas.HistorialEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class HistorialEmpresaService {
    private final HistorialEmpresaRepository historialEmpresaRepository;

    public HistorialEmpresaService(HistorialEmpresaRepository historialEmpresaRepository) {
        this.historialEmpresaRepository = historialEmpresaRepository;
    }

    public List<HistorialEmpresaEntity> getAllHistorialEmpresa() {
        return historialEmpresaRepository.findAll();
    }

    public HistorialEmpresaEntity getHistorialEmpresaById(UUID id) {
        return historialEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HistorialEmpresa no encontrado"));
    }

    public HistorialEmpresaEntity createHistorialEmpresa(CreateHistorialEmpresaDTO dto) {
        HistorialEmpresaEntity historial = HistorialEmpresaEntity.builder()
                .descripcion(dto.descripcion)
                .fecha(dto.fecha)
                .build();
        return historialEmpresaRepository.save(historial);
    }

    public HistorialEmpresaEntity updateHistorialEmpresa(UUID id, CreateHistorialEmpresaDTO dto) {
        HistorialEmpresaEntity historial = getHistorialEmpresaById(id);
        historial.setDescripcion(dto.descripcion);
        historial.setFecha(dto.fecha);
        return historialEmpresaRepository.save(historial);
    }

    public void deleteHistorialEmpresa(UUID id) {
        if (!historialEmpresaRepository.existsById(id)) {
            throw new RuntimeException("HistorialEmpresa no encontrado");
        }
        historialEmpresaRepository.deleteById(id);
    }
}
