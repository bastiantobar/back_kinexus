package com.kinexus.back.repository.pacientes;

import com.kinexus.back.model.pacientes.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CitaRepository extends JpaRepository<CitaEntity, UUID> {
}
