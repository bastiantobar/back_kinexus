package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.HistorialEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HistorialEmpresaRepository extends JpaRepository<HistorialEmpresaEntity, UUID> {}
