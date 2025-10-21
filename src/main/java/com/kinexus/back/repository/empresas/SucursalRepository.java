package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.SucursalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import java.util.List;

public interface SucursalRepository extends JpaRepository<SucursalEntity, UUID> {
	List<SucursalEntity> findByPlan_Id(UUID planId);
	List<SucursalEntity> findByPlan_Empresa_Id(UUID empresaId);
}
