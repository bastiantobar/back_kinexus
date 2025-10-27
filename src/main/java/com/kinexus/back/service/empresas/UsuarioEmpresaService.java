package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateUsuarioEmpresaDTO;
import com.kinexus.back.repository.empresas.SesionEmpresaRepository;
import com.kinexus.back.repository.empresas.SesionTrabajadorRepository;
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
    private final SesionEmpresaRepository sesionEmpresaRepository;
    private final SesionTrabajadorRepository sesionTrabajadorRepository;

    public UsuarioEmpresaService(UsuarioEmpresaRepository usuarioEmpresaRepository,
                                 SucursalRepository sucursalRepository,
                                 SesionEmpresaRepository sesionEmpresaRepository,
                                 SesionTrabajadorRepository sesionTrabajadorRepository) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.sucursalRepository = sucursalRepository;
        this.sesionEmpresaRepository = sesionEmpresaRepository;
        this.sesionTrabajadorRepository = sesionTrabajadorRepository;
    }


    public List<UsuarioEmpresaEntity> getUsuariosBySucursalId(UUID sucursalId) {
        return usuarioEmpresaRepository.findBySucursal_Id(sucursalId);
    }

    public List<UsuarioEmpresaEntity> getUsuariosByPlanId(UUID planId) {
        return usuarioEmpresaRepository.findBySucursal_Plan_Id(planId);
    }

    public List<UsuarioEmpresaEntity> getUsuariosByEmpresaId(UUID empresaId) {
        return usuarioEmpresaRepository.findBySucursal_Plan_Empresa_Id(empresaId);
    }

    public UsuarioEmpresaEntity getUsuarioEmpresaById(UUID id) {
        return usuarioEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioEmpresa no encontrado"));
    }

    public UsuarioEmpresaEntity createUsuarioEmpresa(CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity usuario = UsuarioEmpresaEntity.builder()
                .nombre(dto.nombre)
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
            sucursal.setNumeroTrabajadores(sucursal.getTrabajadores().size());
            sucursalRepository.save(sucursal);

            // Crear SesionTrabajador para cada SesionEmpresa existente en la sucursal
            java.util.List<SesionEmpresaEntity> sesiones = sesionEmpresaRepository.findBySucursal_Id(sucursal.getId());
            if (sesiones != null) {
                if (saved.getAsistencias() == null) saved.setAsistencias(new ArrayList<>());
        for (SesionEmpresaEntity sesion : sesiones) {
            SesionTrabajadorEntity asistencia = SesionTrabajadorEntity.builder()
                            .sesion(sesion)
                            .usuarioEmpresa(saved)
                .asistencia(Boolean.FALSE)
                .descripcionClinica("")
                            .build();
                    SesionTrabajadorEntity asistenciaGuardada = sesionTrabajadorRepository.save(asistencia);
                    // Actualizar colecciones bidireccionales en memoria
                    saved.getAsistencias().add(asistenciaGuardada);
                    if (sesion.getAsistencias() == null) sesion.setAsistencias(new ArrayList<>());
                    sesion.getAsistencias().add(asistenciaGuardada);
                }
            }

            return saved;
        }

        return usuarioEmpresaRepository.save(usuario);
    }

    public UsuarioEmpresaEntity updateUsuarioEmpresa(UUID id, CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity usuario = getUsuarioEmpresaById(id);

        usuario.setNombre(dto.nombre);
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
                    oldSucursal.setNumeroTrabajadores(oldSucursal.getTrabajadores().size());
                    sucursalRepository.save(oldSucursal);
                }

                usuario.setSucursal(newSucursal);
                UsuarioEmpresaEntity saved = usuarioEmpresaRepository.save(usuario);

                if (newSucursal.getTrabajadores() == null) newSucursal.setTrabajadores(new ArrayList<>());
                boolean exists = newSucursal.getTrabajadores().stream().anyMatch(u -> u.getId().equals(saved.getId()));
                if (!exists) newSucursal.getTrabajadores().add(saved);
                newSucursal.setNumeroTrabajadores(newSucursal.getTrabajadores().size());
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
                sucursal.setNumeroTrabajadores(sucursal.getTrabajadores().size());
                sucursalRepository.save(sucursal);
            }
        }

        usuarioEmpresaRepository.deleteById(id);
    }
}
