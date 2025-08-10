package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.dto.empresas.CreateSucursalDTO;
import com.kinexus.back.repository.empresas.SucursalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SucursalService {
    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
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
        return sucursalRepository.save(sucursal);
    }

    public SucursalEntity updateSucursal(UUID id, CreateSucursalDTO dto) {
        SucursalEntity sucursal = getSucursalById(id);
        sucursal.setNumeroTrabajadores(dto.numeroTrabajadores);
        sucursal.setEmail(dto.email);
        sucursal.setTelefono(dto.telefono);
        sucursal.setDireccion(dto.direccion);
        return sucursalRepository.save(sucursal);
    }

    public void deleteSucursal(UUID id) {
        if (!sucursalRepository.existsById(id)) {
            throw new RuntimeException("Sucursal no encontrada");
        }
        sucursalRepository.deleteById(id);
    }
}
