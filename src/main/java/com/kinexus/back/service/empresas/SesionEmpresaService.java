// ...existing code...
package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import com.kinexus.back.model.empresas.SucursalEntity;
import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateSesionEmpresaDTO;
import com.kinexus.back.repository.empresas.SesionEmpresaRepository;
import com.kinexus.back.repository.empresas.SesionTrabajadorRepository;
import com.kinexus.back.repository.empresas.SucursalRepository;
import com.kinexus.back.repository.empresas.UsuarioEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SesionEmpresaService {
    public List<SesionEmpresaEntity> getSesionesBySucursalId(UUID sucursalId) {
        return sesionEmpresaRepository.findBySucursal_Id(sucursalId);
    }

    public List<SesionEmpresaEntity> getSesionesByPlanId(UUID planId) {
        return sesionEmpresaRepository.findBySucursal_Plan_Id(planId);
    }

    public List<SesionEmpresaEntity> getSesionesByEmpresaId(UUID empresaId) {
        return sesionEmpresaRepository.findBySucursal_Plan_Empresa_Id(empresaId);
    }
    private final SesionEmpresaRepository sesionEmpresaRepository;
    private final SucursalRepository sucursalRepository;
    private final UsuarioEmpresaRepository usuarioEmpresaRepository;
    private final SesionTrabajadorRepository sesionTrabajadorRepository;

    public SesionEmpresaService(
            SesionEmpresaRepository sesionEmpresaRepository,
            SucursalRepository sucursalRepository,
            UsuarioEmpresaRepository usuarioEmpresaRepository,
            SesionTrabajadorRepository sesionTrabajadorRepository
    ) {
        this.sesionEmpresaRepository = sesionEmpresaRepository;
        this.sucursalRepository = sucursalRepository;
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.sesionTrabajadorRepository = sesionTrabajadorRepository;
    }

    public List<SesionEmpresaEntity> getAllSesionesEmpresa() {
        return sesionEmpresaRepository.findAll();
    }

    public SesionEmpresaEntity getSesionEmpresaById(UUID id) {
        return sesionEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SesionEmpresa no encontrada"));
    }

    public SesionEmpresaEntity createSesionEmpresa(CreateSesionEmpresaDTO dto) {
    SesionEmpresaEntity sesion = SesionEmpresaEntity.builder()
        .fechaHora(dto.fechaHora)
        .estado(dto.estado)
        .descripcionClinica(dto.descripcionClinica)
        .asistencia(0)
        .build();

        // Asociar con sucursal si viene sucursalId
        if (dto.sucursalId != null && !dto.sucursalId.isEmpty()) {
            SucursalEntity sucursal = sucursalRepository.findById(UUID.fromString(dto.sucursalId))
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            sesion.setSucursal(sucursal);
            SesionEmpresaEntity saved = sesionEmpresaRepository.save(sesion);

            if (sucursal.getSesiones() == null) sucursal.setSesiones(new java.util.ArrayList<>());
            sucursal.getSesiones().add(saved);
            sucursalRepository.save(sucursal);

            // Crear SesionTrabajador para cada usuario de la sucursal
            java.util.List<UsuarioEmpresaEntity> usuarios = usuarioEmpresaRepository.findBySucursal_Id(sucursal.getId());
            if (usuarios != null) {
                if (saved.getAsistencias() == null) saved.setAsistencias(new java.util.ArrayList<>());
        for (UsuarioEmpresaEntity usuario : usuarios) {
            SesionTrabajadorEntity asistencia = SesionTrabajadorEntity.builder()
                            .sesion(saved)
                            .usuarioEmpresa(usuario)
                .asistencia(Boolean.FALSE)
                .descripcionClinica("")
                            .build();
                    SesionTrabajadorEntity asistenciaGuardada = sesionTrabajadorRepository.save(asistencia);

                    // Actualizar colecciones bidireccionales en memoria
                    saved.getAsistencias().add(asistenciaGuardada);
                    if (usuario.getAsistencias() == null) usuario.setAsistencias(new java.util.ArrayList<>());
                    usuario.getAsistencias().add(asistenciaGuardada);
                }
            }

            return saved;
        }

        return sesionEmpresaRepository.save(sesion);
    }

    public SesionEmpresaEntity updateSesionEmpresa(UUID id, CreateSesionEmpresaDTO dto) {
        SesionEmpresaEntity sesion = getSesionEmpresaById(id);
    sesion.setFechaHora(dto.fechaHora);
    sesion.setEstado(dto.estado);
    sesion.setDescripcionClinica(dto.descripcionClinica);
        return sesionEmpresaRepository.save(sesion);
    }

    public void deleteSesionEmpresa(UUID id) {
        if (!sesionEmpresaRepository.existsById(id)) {
            throw new RuntimeException("SesionEmpresa no encontrada");
        }
        sesionEmpresaRepository.deleteById(id);
    }
}
