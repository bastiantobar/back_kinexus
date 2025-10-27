package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SesionEmpresaRepository extends JpaRepository<SesionEmpresaEntity, UUID> {
	java.util.List<SesionEmpresaEntity> findBySucursal_Id(UUID sucursalId);
	java.util.List<SesionEmpresaEntity> findBySucursal_Plan_Id(UUID planId);
	java.util.List<SesionEmpresaEntity> findBySucursal_Plan_Empresa_Id(UUID empresaId);
}
