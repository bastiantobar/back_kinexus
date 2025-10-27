package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.PlanEmpresaEntity;
import com.kinexus.back.model.empresas.EmpresaEntity;
import com.kinexus.back.repository.empresas.EmpresaRepository;
import com.kinexus.back.dto.empresas.CreatePlanEmpresaDTO;
import com.kinexus.back.repository.empresas.PlanEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PlanEmpresaService {
    private final PlanEmpresaRepository planEmpresaRepository;
    private final EmpresaRepository empresaRepository;

    public PlanEmpresaService(PlanEmpresaRepository planEmpresaRepository, EmpresaRepository empresaRepository) {
        this.planEmpresaRepository = planEmpresaRepository;
        this.empresaRepository = empresaRepository;
    }


    public List<PlanEmpresaEntity> getPlanesByEmpresaId(UUID empresaId) {
        return planEmpresaRepository.findByEmpresa_Id(empresaId);
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

        // Si viene empresaId, asociar el plan con la empresa y actualizar la colección
        if (dto.empresaId != null && !dto.empresaId.isEmpty()) {
            UUID empresaUuid = UUID.fromString(dto.empresaId);
            EmpresaEntity empresa = empresaRepository.findById(empresaUuid)
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            plan.setEmpresa(empresa);
            PlanEmpresaEntity saved = planEmpresaRepository.save(plan);

            // Asegurar que la lista de planes de la empresa incluye el plan
            if (empresa.getPlanes() == null) {
                empresa.setPlanes(new java.util.ArrayList<>());
            }
            empresa.getPlanes().add(saved);
            empresaRepository.save(empresa);
            return saved;
        }

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
        // Si cambia la empresa asociada, actualizar las colecciones de ambas empresas
        if (dto.empresaId != null) {
            UUID newEmpresaId = UUID.fromString(dto.empresaId);
            EmpresaEntity newEmpresa = empresaRepository.findById(newEmpresaId)
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            EmpresaEntity oldEmpresa = plan.getEmpresa();
            if (oldEmpresa != null && !oldEmpresa.getId().equals(newEmpresa.getId())) {
                // eliminar de la lista antigua
                if (oldEmpresa.getPlanes() != null) {
                    oldEmpresa.getPlanes().removeIf(p -> p.getId().equals(plan.getId()));
                    empresaRepository.save(oldEmpresa);
                }
            }

            // asociar con la nueva empresa si es distinto
            plan.setEmpresa(newEmpresa);
            PlanEmpresaEntity saved = planEmpresaRepository.save(plan);
            if (newEmpresa.getPlanes() == null) {
                newEmpresa.setPlanes(new java.util.ArrayList<>());
            }
            // evitar duplicados
            boolean exists = newEmpresa.getPlanes().stream().anyMatch(p -> p.getId().equals(saved.getId()));
            if (!exists) newEmpresa.getPlanes().add(saved);
            empresaRepository.save(newEmpresa);
            return saved;
        }

        return planEmpresaRepository.save(plan);
    }

    public void deletePlan(UUID id) {
        if (!planEmpresaRepository.existsById(id)) {
            throw new RuntimeException("Plan de empresa no encontrado");
        }
        // Antes de borrar, desasociar de la empresa si existe
        PlanEmpresaEntity plan = planEmpresaRepository.findById(id).orElse(null);
        if (plan != null && plan.getEmpresa() != null) {
            EmpresaEntity empresa = plan.getEmpresa();
            if (empresa.getPlanes() != null) {
                empresa.getPlanes().removeIf(p -> p.getId().equals(id));
                empresaRepository.save(empresa);
            }
        }
        planEmpresaRepository.deleteById(id);
    }
}
