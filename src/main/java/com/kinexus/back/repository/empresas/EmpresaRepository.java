package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, UUID> {}
