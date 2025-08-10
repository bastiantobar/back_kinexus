package com.kinexus.back.repository.pacientes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kinexus.back.model.pacientes.FichaMedicaEntity;

import java.util.UUID;

public interface FichaMedicaRepository extends JpaRepository<FichaMedicaEntity, UUID> {
}
