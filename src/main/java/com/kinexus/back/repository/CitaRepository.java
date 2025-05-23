package com.kinexus.back.repository;

import com.kinexus.back.model.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CitaRepository extends JpaRepository<CitaEntity, UUID> {
}
