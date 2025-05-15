package com.kinexus.back.repository;

import com.kinexus.back.model.FichaMedicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FichaMedicaRepository extends JpaRepository<FichaMedicaEntity, UUID> {
}
