package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import java.util.List;

public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresaEntity, UUID> {
	List<UsuarioEmpresaEntity> findBySucursal_Id(UUID sucursalId);
	List<UsuarioEmpresaEntity> findBySucursal_Plan_Id(UUID planId);
	List<UsuarioEmpresaEntity> findBySucursal_Plan_Empresa_Id(UUID empresaId);
}
