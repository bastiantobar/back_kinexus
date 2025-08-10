package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import com.kinexus.back.model.empresas.SesionTrabajadorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionTrabajadorRepository extends JpaRepository<SesionTrabajadorEntity, SesionTrabajadorId> {}
