package com.kinexus.back.repository;

import com.kinexus.back.model.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PagoRepository extends JpaRepository<PagoEntity, UUID> {
}
