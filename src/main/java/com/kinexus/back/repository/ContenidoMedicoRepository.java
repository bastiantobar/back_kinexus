package com.kinexus.back.repository;

import com.kinexus.back.model.ContenidoMedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ContenidoMedicoRepository extends JpaRepository<ContenidoMedicoEntity, UUID> {
}
