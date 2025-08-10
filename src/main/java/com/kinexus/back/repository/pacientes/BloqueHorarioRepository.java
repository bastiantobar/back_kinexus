package com.kinexus.back.repository.pacientes;

import com.kinexus.back.model.pacientes.BloqueHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BloqueHorarioRepository extends JpaRepository<BloqueHorarioEntity, UUID> {
}
