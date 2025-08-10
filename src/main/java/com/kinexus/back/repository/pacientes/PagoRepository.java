package com.kinexus.back.repository.pacientes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kinexus.back.model.pacientes.PagoEntity;

import java.util.UUID;

public interface PagoRepository extends JpaRepository<PagoEntity, UUID> {
}
