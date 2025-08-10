package com.kinexus.back.service.pacientes;

import com.kinexus.back.model.pacientes.SesionEntity;
import com.kinexus.back.repository.pacientes.SesionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SesionService {
    private final SesionRepository sesionRepository;

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public List<SesionEntity> getAll() {
        return sesionRepository.findAll();
    }

    public SesionEntity getById(UUID id) {
        return sesionRepository.findById(id).orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
    }

    public SesionEntity create(SesionEntity sesion) {
        return sesionRepository.save(sesion);
    }

    public SesionEntity update(UUID id, SesionEntity sesion) {
        SesionEntity existing = getById(id);
        // Actualiza campos según necesidad
        return sesionRepository.save(existing);
    }

    public void delete(UUID id) {
        sesionRepository.deleteById(id);
    }
}
