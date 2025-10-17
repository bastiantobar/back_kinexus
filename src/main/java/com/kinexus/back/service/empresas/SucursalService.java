package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.EmpresaEntity;
import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.dto.empresas.CreateSucursalDTO;
import com.kinexus.back.repository.empresas.SucursalRepository;
import com.kinexus.back.repository.empresas.EmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SucursalService {
    private final SucursalRepository sucursalRepository;
    private final EmpresaRepository empresaRepository;

    public SucursalService(SucursalRepository sucursalRepository, EmpresaRepository empresaRepository) {
        this.sucursalRepository = sucursalRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<SucursalEntity> getAllSucursales() {
        return sucursalRepository.findAll();
    }

    public SucursalEntity getSucursalById(UUID id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    public SucursalEntity createSucursal(CreateSucursalDTO dto) {
        SucursalEntity sucursal = SucursalEntity.builder()
                .numeroTrabajadores(dto.numeroTrabajadores)
                .email(dto.email)
                .telefono(dto.telefono)
                .direccion(dto.direccion)
                .build();

        // Asociar con empresa si viene empresaId
        if (dto.empresaId != null && !dto.empresaId.isEmpty()) {
            UUID empresaId = UUID.fromString(dto.empresaId);
            EmpresaEntity empresa = empresaRepository.findById(empresaId)
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            sucursal.setEmpresa(empresa);
            SucursalEntity saved = sucursalRepository.save(sucursal);

            // Asegurar que la lista de sucursales de la empresa incluya la nueva sucursal
            if (empresa.getSucursales() == null) empresa.setSucursales(new ArrayList<>());
            empresa.getSucursales().add(saved);
            empresaRepository.save(empresa);
            return saved;
        }

        return sucursalRepository.save(sucursal);
    }

    public SucursalEntity updateSucursal(UUID id, CreateSucursalDTO dto) {
        SucursalEntity sucursal = getSucursalById(id);
        sucursal.setNumeroTrabajadores(dto.numeroTrabajadores);
        sucursal.setEmail(dto.email);
        sucursal.setTelefono(dto.telefono);
        sucursal.setDireccion(dto.direccion);

        // Si cambia la empresa asociada, actualizar colecciones de ambas
        if (dto.empresaId != null) {
            UUID newEmpresaId = UUID.fromString(dto.empresaId);
            EmpresaEntity newEmpresa = empresaRepository.findById(newEmpresaId)
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            EmpresaEntity oldEmpresa = sucursal.getEmpresa();
            if (oldEmpresa != null && !oldEmpresa.getId().equals(newEmpresa.getId())) {
                if (oldEmpresa.getSucursales() != null) {
                    oldEmpresa.getSucursales().removeIf(s -> s.getId().equals(sucursal.getId()));
                    empresaRepository.save(oldEmpresa);
                }
            }

            sucursal.setEmpresa(newEmpresa);
            SucursalEntity saved = sucursalRepository.save(sucursal);

            if (newEmpresa.getSucursales() == null) newEmpresa.setSucursales(new ArrayList<>());
            boolean exists = newEmpresa.getSucursales().stream().anyMatch(s -> s.getId().equals(saved.getId()));
            if (!exists) newEmpresa.getSucursales().add(saved);
            empresaRepository.save(newEmpresa);

            return saved;
        }

        return sucursalRepository.save(sucursal);
    }

    public void deleteSucursal(UUID id) {
        if (!sucursalRepository.existsById(id)) {
            throw new RuntimeException("Sucursal no encontrada");
        }

        SucursalEntity sucursal = sucursalRepository.findById(id).orElse(null);
        if (sucursal != null && sucursal.getEmpresa() != null) {
            EmpresaEntity empresa = sucursal.getEmpresa();
            if (empresa.getSucursales() != null) {
                empresa.getSucursales().removeIf(s -> s.getId().equals(id));
                empresaRepository.save(empresa);
            }
        }

        sucursalRepository.deleteById(id);
    }
}
