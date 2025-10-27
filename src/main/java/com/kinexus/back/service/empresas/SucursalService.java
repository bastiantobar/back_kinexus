package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.PlanEmpresaEntity;
import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.dto.empresas.CreateSucursalDTO;
import com.kinexus.back.repository.empresas.SucursalRepository;
import com.kinexus.back.repository.empresas.PlanEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SucursalService {
    private final SucursalRepository sucursalRepository;
    private final PlanEmpresaRepository planRepository;

    public SucursalService(SucursalRepository sucursalRepository, PlanEmpresaRepository planRepository) {
        this.sucursalRepository = sucursalRepository;
        this.planRepository = planRepository;
    }


    public List<SucursalEntity> getSucursalesByPlanId(UUID planId) {
        return sucursalRepository.findByPlan_Id(planId);
    }

    public List<SucursalEntity> getSucursalesByEmpresaId(UUID empresaId) {
        return sucursalRepository.findByPlan_Empresa_Id(empresaId);
    }

    public SucursalEntity getSucursalById(UUID id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    public SucursalEntity createSucursal(CreateSucursalDTO dto) {
    SucursalEntity sucursal = SucursalEntity.builder()
        .numeroTrabajadores(0)
        .email(dto.email)
                .telefono(dto.telefono)
                .direccion(dto.direccion)
        .responsable(dto.responsable)
                .build();

        // Asociar con plan si viene planId
        if (dto.planId != null && !dto.planId.isEmpty()) {
            UUID planId = UUID.fromString(dto.planId);
            PlanEmpresaEntity plan = planRepository.findById(planId)
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
            sucursal.setPlan(plan);
            SucursalEntity saved = sucursalRepository.save(sucursal);

            // Asegurar que la lista de sucursales del plan incluya la nueva sucursal
            if (plan.getSucursales() == null) plan.setSucursales(new ArrayList<>());
            plan.getSucursales().add(saved);
            planRepository.save(plan);
            return saved;
        }

        return sucursalRepository.save(sucursal);
    }

    public SucursalEntity updateSucursal(UUID id, CreateSucursalDTO dto) {
        SucursalEntity sucursal = getSucursalById(id);
        sucursal.setEmail(dto.email);
        sucursal.setTelefono(dto.telefono);
        sucursal.setDireccion(dto.direccion);
    sucursal.setResponsable(dto.responsable);

        // Si cambia el plan asociado, actualizar colecciones de ambos planes
        if (dto.planId != null) {
            UUID newPlanId = UUID.fromString(dto.planId);
            PlanEmpresaEntity newPlan = planRepository.findById(newPlanId)
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

            PlanEmpresaEntity oldPlan = sucursal.getPlan();
            if (oldPlan != null && !oldPlan.getId().equals(newPlan.getId())) {
                if (oldPlan.getSucursales() != null) {
                    oldPlan.getSucursales().removeIf(s -> s.getId().equals(sucursal.getId()));
                    planRepository.save(oldPlan);
                }
            }

            sucursal.setPlan(newPlan);
            SucursalEntity saved = sucursalRepository.save(sucursal);

            if (newPlan.getSucursales() == null) newPlan.setSucursales(new ArrayList<>());
            boolean exists = newPlan.getSucursales().stream().anyMatch(s -> s.getId().equals(saved.getId()));
            if (!exists) newPlan.getSucursales().add(saved);
            planRepository.save(newPlan);

            return saved;
        }

        return sucursalRepository.save(sucursal);
    }

    public void deleteSucursal(UUID id) {
        if (!sucursalRepository.existsById(id)) {
            throw new RuntimeException("Sucursal no encontrada");
        }

        SucursalEntity sucursal = sucursalRepository.findById(id).orElse(null);
        if (sucursal != null && sucursal.getPlan() != null) {
            PlanEmpresaEntity plan = sucursal.getPlan();
            if (plan.getSucursales() != null) {
                plan.getSucursales().removeIf(s -> s.getId().equals(id));
                planRepository.save(plan);
            }
        }

        sucursalRepository.deleteById(id);
    }
}
