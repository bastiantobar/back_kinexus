package com.kinexus.back.repository.empresas;

import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresaEntity, UUID> {}
