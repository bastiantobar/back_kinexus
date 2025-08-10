
package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.SesionTrabajadorEntity;
import com.kinexus.back.model.empresas.SesionTrabajadorId;
import com.kinexus.back.dto.empresas.CreateSesionTrabajadorDTO;
import com.kinexus.back.repository.empresas.SesionTrabajadorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SesionTrabajadorService {
    private final SesionTrabajadorRepository sesionTrabajadorRepository;

    public SesionTrabajadorService(SesionTrabajadorRepository sesionTrabajadorRepository) {
        this.sesionTrabajadorRepository = sesionTrabajadorRepository;
    }

    public List<SesionTrabajadorEntity> getAllSesionesTrabajador() {
        return sesionTrabajadorRepository.findAll();
    }

    public SesionTrabajadorEntity getSesionTrabajadorById(SesionTrabajadorId id) {
        return sesionTrabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SesionTrabajador no encontrada"));
    }

    public SesionTrabajadorEntity createSesionTrabajador(CreateSesionTrabajadorDTO dto) {
        SesionTrabajadorId id = new SesionTrabajadorId(
            java.util.UUID.fromString(dto.sesionId),
            java.util.UUID.fromString(dto.usuarioEmpresaId)
        );
        SesionTrabajadorEntity sesion = SesionTrabajadorEntity.builder()
                .id(id)
                .asistencia(dto.asistencia)
                .build();
        return sesionTrabajadorRepository.save(sesion);
    }

    public SesionTrabajadorEntity updateSesionTrabajador(SesionTrabajadorId id, CreateSesionTrabajadorDTO dto) {
        SesionTrabajadorEntity sesion = getSesionTrabajadorById(id);
        sesion.setAsistencia(dto.asistencia);
        return sesionTrabajadorRepository.save(sesion);
    }

    public void deleteSesionTrabajador(SesionTrabajadorId id) {
        if (!sesionTrabajadorRepository.existsById(id)) {
            throw new RuntimeException("SesionTrabajador no encontrada");
        }
        sesionTrabajadorRepository.deleteById(id);
    }
}
