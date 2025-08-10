package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.PlanEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PlanEmpresaRepository extends JpaRepository<PlanEmpresaEntity, UUID> {}
