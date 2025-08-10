package com.kinexus.back.service.pacientes;

import com.kinexus.back.model.pacientes.PlanEntity;
import com.kinexus.back.repository.pacientes.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanEntity> getAll() {
        return planRepository.findAll();
    }

    public PlanEntity getById(UUID id) {
        return planRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan no encontrado"));
    }

    public PlanEntity create(PlanEntity plan) {
        return planRepository.save(plan);
    }

    public PlanEntity update(UUID id, PlanEntity plan) {
        PlanEntity existing = getById(id);
        // Actualiza campos según necesidad
        return planRepository.save(existing);
    }

    public void delete(UUID id) {
        planRepository.deleteById(id);
    }
}
