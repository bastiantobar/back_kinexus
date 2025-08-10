package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SesionEmpresaRepository extends JpaRepository<SesionEmpresaEntity, UUID> {}
