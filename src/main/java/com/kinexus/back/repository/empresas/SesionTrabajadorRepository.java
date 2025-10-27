package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionTrabajadorRepository extends JpaRepository<SesionTrabajadorEntity, UUID> {
	List<SesionTrabajadorEntity> findByUsuarioEmpresa_Id(UUID usuarioEmpresaId);
	List<SesionTrabajadorEntity> findBySesion_Id(UUID sesionEmpresaId);
	List<SesionTrabajadorEntity> findBySesion_Sucursal_Id(UUID sucursalId);
	List<SesionTrabajadorEntity> findBySesion_Sucursal_Plan_Id(UUID planId);
	List<SesionTrabajadorEntity> findBySesion_Sucursal_Plan_Empresa_Id(UUID empresaId);
}
