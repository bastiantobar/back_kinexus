package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.SucursalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SucursalRepository extends JpaRepository<SucursalEntity, UUID> {}
