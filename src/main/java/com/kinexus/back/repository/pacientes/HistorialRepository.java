package com.kinexus.back.repository.pacientes;

import com.kinexus.back.model.pacientes.HistorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HistorialRepository extends JpaRepository<HistorialEntity, UUID> {
}
