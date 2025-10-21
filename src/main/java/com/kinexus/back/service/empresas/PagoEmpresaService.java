package com.kinexus.back.service.empresas;

import com.kinexus.back.dto.empresas.CreatePagoEmpresaDTO;
import com.kinexus.back.model.empresas.EmpresaEntity;
import com.kinexus.back.model.empresas.PagoEmpresaEntity;
import com.kinexus.back.model.empresas.PlanEmpresaEntity;
import com.kinexus.back.repository.empresas.EmpresaRepository;
import com.kinexus.back.repository.empresas.PagoEmpresaRepository;
import com.kinexus.back.repository.empresas.PlanEmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PagoEmpresaService {
    private final PagoEmpresaRepository pagoEmpresaRepository;
    private final PlanEmpresaRepository planRepository;

    public PagoEmpresaService(PagoEmpresaRepository pagoEmpresaRepository,
                              PlanEmpresaRepository planRepository) {
        this.pagoEmpresaRepository = pagoEmpresaRepository;
        this.planRepository = planRepository;
    }


    public List<PagoEmpresaEntity> getPagosByPlanId(UUID planId) {
        return pagoEmpresaRepository.findByPlan_Id(planId);
    }

    public List<PagoEmpresaEntity> getPagosByEmpresaId(UUID empresaId) {
        return pagoEmpresaRepository.findByPlan_Empresa_Id(empresaId);
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

    // Set relation with plan if provided
    if (dto.planId != null) {
        PlanEmpresaEntity plan = planRepository.findById(UUID.fromString(dto.planId))
            .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        pago.setPlan(plan);
    }

        PagoEmpresaEntity saved = pagoEmpresaRepository.save(pago);

        // Update parent's collections so a subsequent GET on Plan returns el nuevo pago
        if (saved.getPlan() != null) {
            PlanEmpresaEntity p = saved.getPlan();
            if (p.getPagos() == null) p.setPagos(new ArrayList<>());
            p.getPagos().add(saved);
            planRepository.save(p);
        }

        return saved;
    }

    public PagoEmpresaEntity updatePagoEmpresa(UUID id, CreatePagoEmpresaDTO dto) {
        PagoEmpresaEntity pago = getPagoEmpresaById(id);

        // keep reference to old plan
        PlanEmpresaEntity oldPlan = pago.getPlan();

        // update fields
        pago.setMonto(dto.monto);
        pago.setFechaPago(dto.fechaPago);
        pago.setMetodoPago(dto.metodoPago);
        pago.setEstadoPago(dto.estadoPago);

        // handle plan change
        if (dto.planId != null) {
            UUID newPlanId = UUID.fromString(dto.planId);
            if (oldPlan == null || !oldPlan.getId().equals(newPlanId)) {
                PlanEmpresaEntity newPlan = planRepository.findById(newPlanId)
                        .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
                pago.setPlan(newPlan);

                if (oldPlan != null && oldPlan.getPagos() != null) {
                    oldPlan.getPagos().removeIf(px -> px.getId() != null && px.getId().equals(pago.getId()));
                    planRepository.save(oldPlan);
                }

                if (newPlan.getPagos() == null) newPlan.setPagos(new ArrayList<>());
                newPlan.getPagos().add(pago);
                planRepository.save(newPlan);
            }
        }

        PagoEmpresaEntity saved = pagoEmpresaRepository.save(pago);
        return saved;
    }

    public void deletePagoEmpresa(UUID id) {
        PagoEmpresaEntity pago = getPagoEmpresaById(id);

        PlanEmpresaEntity plan = pago.getPlan();

        // remove from plan collection
        if (plan != null && plan.getPagos() != null) {
            plan.getPagos().removeIf(px -> px.getId() != null && px.getId().equals(pago.getId()));
            planRepository.save(plan);
        }

        pagoEmpresaRepository.deleteById(id);
    }
}
