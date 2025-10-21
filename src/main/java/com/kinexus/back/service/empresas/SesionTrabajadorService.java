package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateSesionTrabajadorDTO;
import com.kinexus.back.repository.empresas.SesionEmpresaRepository;
import com.kinexus.back.repository.empresas.SesionTrabajadorRepository;
import com.kinexus.back.repository.empresas.UsuarioEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SesionTrabajadorService {
    private final SesionTrabajadorRepository sesionTrabajadorRepository;
    private final UsuarioEmpresaRepository usuarioEmpresaRepository;
    private final SesionEmpresaRepository sesionEmpresaRepository;

    public SesionTrabajadorService(SesionTrabajadorRepository sesionTrabajadorRepository,
                                   UsuarioEmpresaRepository usuarioEmpresaRepository,
                                   SesionEmpresaRepository sesionEmpresaRepository) {
        this.sesionTrabajadorRepository = sesionTrabajadorRepository;
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.sesionEmpresaRepository = sesionEmpresaRepository;
    }

    public List<SesionTrabajadorEntity> getSesionesByUsuarioEmpresaId(UUID usuarioEmpresaId) {
        return sesionTrabajadorRepository.findByUsuarioEmpresa_Id(usuarioEmpresaId);
    }

    public List<SesionTrabajadorEntity> getSesionesBySesionEmpresaId(UUID sesionEmpresaId) {
        return sesionTrabajadorRepository.findBySesion_Id(sesionEmpresaId);
    }

    public List<SesionTrabajadorEntity> getSesionesBySucursalId(UUID sucursalId) {
        return sesionTrabajadorRepository.findBySesion_Sucursal_Id(sucursalId);
    }

    public List<SesionTrabajadorEntity> getSesionesByPlanId(UUID planId) {
        return sesionTrabajadorRepository.findBySesion_Sucursal_Plan_Id(planId);
    }

    public List<SesionTrabajadorEntity> getSesionesByEmpresaId(UUID empresaId) {
        return sesionTrabajadorRepository.findBySesion_Sucursal_Plan_Empresa_Id(empresaId);
    }

    public SesionTrabajadorEntity getSesionTrabajadorById(UUID id) {
        return sesionTrabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SesionTrabajador no encontrada"));
    }

    public SesionTrabajadorEntity createSesionTrabajador(CreateSesionTrabajadorDTO dto) {
        if (dto.sesionId == null || dto.sesionId.isEmpty()) {
            throw new RuntimeException("sesionId es requerido");
        }
        if (dto.usuarioEmpresaId == null || dto.usuarioEmpresaId.isEmpty()) {
            throw new RuntimeException("usuarioEmpresaId es requerido");
        }

        UUID sesionEmpresaId = UUID.fromString(dto.sesionId);
        UUID usuarioId = UUID.fromString(dto.usuarioEmpresaId);

        SesionEmpresaEntity sesionEmpresa = sesionEmpresaRepository.findById(sesionEmpresaId)
                .orElseThrow(() -> new RuntimeException("SesionEmpresa no encontrada"));
        UsuarioEmpresaEntity usuario = usuarioEmpresaRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("UsuarioEmpresa no encontrado"));

    SesionTrabajadorEntity nueva = SesionTrabajadorEntity.builder()
        .sesion(sesionEmpresa)
        .usuarioEmpresa(usuario)
        .asistencia(dto.asistencia != null ? dto.asistencia : Boolean.FALSE)
        .build();

        SesionTrabajadorEntity saved = sesionTrabajadorRepository.save(nueva);

        // actualizar colecciones en memoria
        if (usuario.getAsistencias() == null) usuario.setAsistencias(new java.util.ArrayList<>());
        usuario.getAsistencias().add(saved);
        if (sesionEmpresa.getAsistencias() == null) sesionEmpresa.setAsistencias(new java.util.ArrayList<>());
        sesionEmpresa.getAsistencias().add(saved);

        return saved;
    }

    public SesionTrabajadorEntity updateSesionTrabajador(UUID id, CreateSesionTrabajadorDTO dto) {
        SesionTrabajadorEntity sesion = getSesionTrabajadorById(id);
        sesion.setAsistencia(dto.asistencia);
        return sesionTrabajadorRepository.save(sesion);
    }

    public void deleteSesionTrabajador(UUID id) {
        if (!sesionTrabajadorRepository.existsById(id)) {
            throw new RuntimeException("SesionTrabajador no encontrada");
        }
        sesionTrabajadorRepository.deleteById(id);
    }
}
