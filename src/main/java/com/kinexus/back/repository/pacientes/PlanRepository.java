package com.kinexus.back.repository.pacientes;

import com.kinexus.back.model.pacientes.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<PlanEntity, UUID> {
}
