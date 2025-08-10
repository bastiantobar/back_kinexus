package com.kinexus.back.repository.pacientes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kinexus.back.model.pacientes.ContenidoMedicoEntity;

import java.util.UUID;

public interface ContenidoMedicoRepository extends JpaRepository<ContenidoMedicoEntity, UUID> {
}
