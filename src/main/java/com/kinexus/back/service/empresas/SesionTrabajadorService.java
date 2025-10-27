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
        .descripcionClinica(dto.descripcionClinica != null ? dto.descripcionClinica : "")
        .build();

        SesionTrabajadorEntity saved = sesionTrabajadorRepository.save(nueva);

        // actualizar contador de asistencia en la sesión empresa si corresponde
        if (Boolean.TRUE.equals(saved.getAsistencia())) {
            Integer count = sesionEmpresa.getAsistencia();
            if (count == null) count = 0;
            sesionEmpresa.setAsistencia(count + 1);
            sesionEmpresaRepository.save(sesionEmpresa);
        }

        // actualizar colecciones en memoria
        if (usuario.getAsistencias() == null) usuario.setAsistencias(new java.util.ArrayList<>());
        usuario.getAsistencias().add(saved);
        if (sesionEmpresa.getAsistencias() == null) sesionEmpresa.setAsistencias(new java.util.ArrayList<>());
        sesionEmpresa.getAsistencias().add(saved);

        return saved;
    }

    public SesionTrabajadorEntity updateSesionTrabajador(UUID id, CreateSesionTrabajadorDTO dto) {
        SesionTrabajadorEntity sesion = getSesionTrabajadorById(id);
        Boolean prev = sesion.getAsistencia() != null ? sesion.getAsistencia() : Boolean.FALSE;

        if (dto.asistencia != null) {
            // ajustar contador de asistencia en la sesión
            SesionEmpresaEntity sesionEmpresa = sesion.getSesion();
            Integer count = sesionEmpresa.getAsistencia();
            if (count == null) count = 0;
            if (Boolean.TRUE.equals(dto.asistencia) && !Boolean.TRUE.equals(prev)) {
                sesionEmpresa.setAsistencia(count + 1);
                sesionEmpresaRepository.save(sesionEmpresa);
            } else if (Boolean.FALSE.equals(dto.asistencia) && Boolean.TRUE.equals(prev)) {
                sesionEmpresa.setAsistencia(Math.max(0, count - 1));
                sesionEmpresaRepository.save(sesionEmpresa);
            }
            sesion.setAsistencia(dto.asistencia);
        }

        if (dto.descripcionClinica != null) {
            sesion.setDescripcionClinica(dto.descripcionClinica);
        }
        return sesionTrabajadorRepository.save(sesion);
    }

    public void deleteSesionTrabajador(UUID id) {
        SesionTrabajadorEntity sesion = sesionTrabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SesionTrabajador no encontrada"));

        // si tenía asistencia true, disminuir el contador
        if (Boolean.TRUE.equals(sesion.getAsistencia())) {
            SesionEmpresaEntity sesionEmpresa = sesion.getSesion();
            Integer count = sesionEmpresa.getAsistencia();
            if (count == null) count = 0;
            sesionEmpresa.setAsistencia(Math.max(0, count - 1));
            sesionEmpresaRepository.save(sesionEmpresa);
        }

        sesionTrabajadorRepository.deleteById(id);
    }
}
