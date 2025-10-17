package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateUsuarioEmpresaDTO;
import com.kinexus.back.repository.empresas.UsuarioEmpresaRepository;
import com.kinexus.back.repository.empresas.SucursalRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioEmpresaService {
    private final UsuarioEmpresaRepository usuarioEmpresaRepository;
    private final SucursalRepository sucursalRepository;

    public UsuarioEmpresaService(UsuarioEmpresaRepository usuarioEmpresaRepository, SucursalRepository sucursalRepository) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.sucursalRepository = sucursalRepository;
    }

    public List<UsuarioEmpresaEntity> getAllUsuariosEmpresa() {
        return usuarioEmpresaRepository.findAll();
    }

    public UsuarioEmpresaEntity getUsuarioEmpresaById(UUID id) {
        return usuarioEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioEmpresa no encontrado"));
    }

    public UsuarioEmpresaEntity createUsuarioEmpresa(CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity usuario = UsuarioEmpresaEntity.builder()
                .nombre(dto.nombre)
                .edad(dto.edad)
                .genero(dto.genero)
                .fechaNacimiento(dto.fechaNacimiento)
                .cargo(dto.cargo)
                .discapacidad(dto.discapacidad)
                .build();

        // Asociar con sucursal si viene sucursalId
        if (dto.sucursalId != null && !dto.sucursalId.isEmpty()) {
            SucursalEntity sucursal = sucursalRepository.findById(UUID.fromString(dto.sucursalId))
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            usuario.setSucursal(sucursal);
            UsuarioEmpresaEntity saved = usuarioEmpresaRepository.save(usuario);

            if (sucursal.getTrabajadores() == null) sucursal.setTrabajadores(new ArrayList<>());
            sucursal.getTrabajadores().add(saved);
            sucursalRepository.save(sucursal);
            return saved;
        }

        return usuarioEmpresaRepository.save(usuario);
    }

    public UsuarioEmpresaEntity updateUsuarioEmpresa(UUID id, CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity usuario = getUsuarioEmpresaById(id);

        usuario.setNombre(dto.nombre);
        usuario.setEdad(dto.edad);
        usuario.setGenero(dto.genero);
        usuario.setFechaNacimiento(dto.fechaNacimiento);
        usuario.setCargo(dto.cargo);
        usuario.setDiscapacidad(dto.discapacidad);

        // manejar cambio de sucursal
        if (dto.sucursalId != null) {
            SucursalEntity oldSucursal = usuario.getSucursal();
            UUID newSucursalId = UUID.fromString(dto.sucursalId);
            if (oldSucursal == null || !oldSucursal.getId().equals(newSucursalId)) {
                SucursalEntity newSucursal = sucursalRepository.findById(newSucursalId)
                        .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
                // remover de la antigua
                if (oldSucursal != null && oldSucursal.getTrabajadores() != null) {
                    oldSucursal.getTrabajadores().removeIf(u -> u.getId().equals(usuario.getId()));
                    sucursalRepository.save(oldSucursal);
                }

                usuario.setSucursal(newSucursal);
                UsuarioEmpresaEntity saved = usuarioEmpresaRepository.save(usuario);

                if (newSucursal.getTrabajadores() == null) newSucursal.setTrabajadores(new ArrayList<>());
                boolean exists = newSucursal.getTrabajadores().stream().anyMatch(u -> u.getId().equals(saved.getId()));
                if (!exists) newSucursal.getTrabajadores().add(saved);
                sucursalRepository.save(newSucursal);
                return saved;
            }
        }

        return usuarioEmpresaRepository.save(usuario);
    }

    public void deleteUsuarioEmpresa(UUID id) {
        if (!usuarioEmpresaRepository.existsById(id)) {
            throw new RuntimeException("UsuarioEmpresa no encontrado");
        }

        UsuarioEmpresaEntity usuario = usuarioEmpresaRepository.findById(id).orElse(null);
        if (usuario != null && usuario.getSucursal() != null) {
            SucursalEntity sucursal = usuario.getSucursal();
            if (sucursal.getTrabajadores() != null) {
                sucursal.getTrabajadores().removeIf(u -> u.getId().equals(id));
                sucursalRepository.save(sucursal);
            }
        }

        usuarioEmpresaRepository.deleteById(id);
    }
}
