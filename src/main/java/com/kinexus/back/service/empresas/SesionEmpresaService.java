package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SesionEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateSesionEmpresaDTO;
import com.kinexus.back.repository.empresas.SesionEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SesionEmpresaService {
    private final SesionEmpresaRepository sesionEmpresaRepository;

    public SesionEmpresaService(SesionEmpresaRepository sesionEmpresaRepository) {
        this.sesionEmpresaRepository = sesionEmpresaRepository;
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
                .asistencia(dto.asistencia)
                .build();
        return sesionEmpresaRepository.save(sesion);
    }

    public SesionEmpresaEntity updateSesionEmpresa(UUID id, CreateSesionEmpresaDTO dto) {
        SesionEmpresaEntity sesion = getSesionEmpresaById(id);
        sesion.setFechaHora(dto.fechaHora);
        sesion.setEstado(dto.estado);
        sesion.setDescripcionClinica(dto.descripcionClinica);
        sesion.setAsistencia(dto.asistencia);
        return sesionEmpresaRepository.save(sesion);
    }

    public void deleteSesionEmpresa(UUID id) {
        if (!sesionEmpresaRepository.existsById(id)) {
            throw new RuntimeException("SesionEmpresa no encontrada");
        }
        sesionEmpresaRepository.deleteById(id);
    }
}
