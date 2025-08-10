package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.EmpresaEntity;
import com.kinexus.back.dto.empresas.CreateEmpresaDTO;
import com.kinexus.back.repository.empresas.EmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<EmpresaEntity> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public EmpresaEntity getEmpresaById(UUID id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    public EmpresaEntity createEmpresa(CreateEmpresaDTO dto) {
        EmpresaEntity empresa = EmpresaEntity.builder()
                .nombre(dto.nombre)
                .ciudad(dto.ciudad)
                .personaACargo(dto.personaACargo)
                .telefono(dto.telefono)
                .email(dto.email)
                .direccionCasaMatriz(dto.direccionCasaMatriz)
                .descripcion(dto.descripcion)
                .build();
        return empresaRepository.save(empresa);
    }

    public EmpresaEntity updateEmpresa(UUID id, CreateEmpresaDTO dto) {
        EmpresaEntity empresa = getEmpresaById(id);
        empresa.setNombre(dto.nombre);
        empresa.setCiudad(dto.ciudad);
        empresa.setPersonaACargo(dto.personaACargo);
        empresa.setTelefono(dto.telefono);
        empresa.setEmail(dto.email);
        empresa.setDireccionCasaMatriz(dto.direccionCasaMatriz);
        empresa.setDescripcion(dto.descripcion);
        return empresaRepository.save(empresa);
    }

    public void deleteEmpresa(UUID id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa no encontrada");
        }
        empresaRepository.deleteById(id);
    }
}
