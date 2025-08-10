package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.PlanEmpresaEntity;
import com.kinexus.back.dto.empresas.CreatePlanEmpresaDTO;
import com.kinexus.back.repository.empresas.PlanEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PlanEmpresaService {
    private final PlanEmpresaRepository planEmpresaRepository;

    public PlanEmpresaService(PlanEmpresaRepository planEmpresaRepository) {
        this.planEmpresaRepository = planEmpresaRepository;
    }

    public List<PlanEmpresaEntity> getAllPlanes() {
        return planEmpresaRepository.findAll();
    }

    public PlanEmpresaEntity getPlanById(UUID id) {
        return planEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de empresa no encontrado"));
    }

    public PlanEmpresaEntity createPlan(CreatePlanEmpresaDTO dto) {
        PlanEmpresaEntity plan = PlanEmpresaEntity.builder()
                .nombre(dto.nombre)
                .descripcion(dto.descripcion)
                .fechaInicio(dto.fechaInicio)
                .fechaTermino(dto.fechaTermino)
                .valor(dto.valor)
                .numeroSesiones(dto.numeroSesiones)
                .build();
        return planEmpresaRepository.save(plan);
    }

    public PlanEmpresaEntity updatePlan(UUID id, CreatePlanEmpresaDTO dto) {
        PlanEmpresaEntity plan = getPlanById(id);
        plan.setNombre(dto.nombre);
        plan.setDescripcion(dto.descripcion);
        plan.setFechaInicio(dto.fechaInicio);
        plan.setFechaTermino(dto.fechaTermino);
        plan.setValor(dto.valor);
        plan.setNumeroSesiones(dto.numeroSesiones);
        return planEmpresaRepository.save(plan);
    }

    public void deletePlan(UUID id) {
        if (!planEmpresaRepository.existsById(id)) {
            throw new RuntimeException("Plan de empresa no encontrado");
        }
        planEmpresaRepository.deleteById(id);
    }
}
