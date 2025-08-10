package com.kinexus.back.repository.pacientes;

import com.kinexus.back.model.pacientes.SesionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SesionRepository extends JpaRepository<SesionEntity, UUID> {
}
