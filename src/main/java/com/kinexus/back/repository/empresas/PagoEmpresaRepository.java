package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.PagoEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PagoEmpresaRepository extends JpaRepository<PagoEmpresaEntity, UUID> {}
