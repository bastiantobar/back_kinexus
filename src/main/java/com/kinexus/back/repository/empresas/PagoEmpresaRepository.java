package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.PagoEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import java.util.List;

public interface PagoEmpresaRepository extends JpaRepository<PagoEmpresaEntity, UUID> {
	List<PagoEmpresaEntity> findByPlan_Id(UUID planId);
	List<PagoEmpresaEntity> findByPlan_Empresa_Id(UUID empresaId);
}
