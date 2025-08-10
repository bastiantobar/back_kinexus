package com.kinexus.back.service.pacientes;

import com.kinexus.back.model.pacientes.HistorialEntity;
import com.kinexus.back.repository.pacientes.HistorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HistorialService {
    private final HistorialRepository historialRepository;

    public HistorialService(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public List<HistorialEntity> getAll() {
        return historialRepository.findAll();
    }

    public HistorialEntity getById(UUID id) {
        return historialRepository.findById(id).orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    public HistorialEntity create(HistorialEntity historial) {
        return historialRepository.save(historial);
    }

    public HistorialEntity update(UUID id, HistorialEntity historial) {
        HistorialEntity existing = getById(id);
        // Actualiza campos según necesidad
        return historialRepository.save(existing);
    }

    public void delete(UUID id) {
        historialRepository.deleteById(id);
    }
}
