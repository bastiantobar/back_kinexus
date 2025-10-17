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
    private final EmpresaRepository empresaRepository;
    private final PlanEmpresaRepository planRepository;

    public PagoEmpresaService(PagoEmpresaRepository pagoEmpresaRepository,
                              EmpresaRepository empresaRepository,
                              PlanEmpresaRepository planRepository) {
        this.pagoEmpresaRepository = pagoEmpresaRepository;
        this.empresaRepository = empresaRepository;
        this.planRepository = planRepository;
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

        // Set relations if provided
        if (dto.empresaId != null) {
            EmpresaEntity empresa = empresaRepository.findById(UUID.fromString(dto.empresaId))
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            pago.setEmpresa(empresa);
        }

        if (dto.planId != null) {
            PlanEmpresaEntity plan = planRepository.findById(UUID.fromString(dto.planId))
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
            pago.setPlan(plan);
        }

        PagoEmpresaEntity saved = pagoEmpresaRepository.save(pago);

        // Update parent's collections so a subsequent GET on Empresa/Plan returns the new pago
        if (saved.getEmpresa() != null) {
            EmpresaEntity e = saved.getEmpresa();
            if (e.getPagos() == null) e.setPagos(new ArrayList<>());
            e.getPagos().add(saved);
            empresaRepository.save(e);
        }

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

        // keep references to old parents
        EmpresaEntity oldEmpresa = pago.getEmpresa();
        PlanEmpresaEntity oldPlan = pago.getPlan();

        // update fields
        pago.setMonto(dto.monto);
        pago.setFechaPago(dto.fechaPago);
        pago.setMetodoPago(dto.metodoPago);
        pago.setEstadoPago(dto.estadoPago);

        // handle empresa change
        if (dto.empresaId != null) {
            UUID newEmpresaId = UUID.fromString(dto.empresaId);
            if (oldEmpresa == null || !oldEmpresa.getId().equals(newEmpresaId)) {
                EmpresaEntity newEmpresa = empresaRepository.findById(newEmpresaId)
                        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
                pago.setEmpresa(newEmpresa);

                // remove from old
                if (oldEmpresa != null && oldEmpresa.getPagos() != null) {
                    oldEmpresa.getPagos().removeIf(px -> px.getId() != null && px.getId().equals(pago.getId()));
                    empresaRepository.save(oldEmpresa);
                }

                // add to new
                if (newEmpresa.getPagos() == null) newEmpresa.setPagos(new ArrayList<>());
                newEmpresa.getPagos().add(pago);
                empresaRepository.save(newEmpresa);
            }
        }

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

        EmpresaEntity empresa = pago.getEmpresa();
        PlanEmpresaEntity plan = pago.getPlan();

        // remove from empresa collection
        if (empresa != null && empresa.getPagos() != null) {
            empresa.getPagos().removeIf(px -> px.getId() != null && px.getId().equals(pago.getId()));
            empresaRepository.save(empresa);
        }

        // remove from plan collection
        if (plan != null && plan.getPagos() != null) {
            plan.getPagos().removeIf(px -> px.getId() != null && px.getId().equals(pago.getId()));
            planRepository.save(plan);
        }

        pagoEmpresaRepository.deleteById(id);
    }
}
