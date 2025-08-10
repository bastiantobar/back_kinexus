package com.kinexus.back.service.pacientes;

import com.kinexus.back.dto.pacientes.CreateContenidoMedicoDTO;
import com.kinexus.back.model.pacientes.ContenidoMedicoEntity;
import com.kinexus.back.repository.pacientes.ContenidoMedicoRepository;
import com.kinexus.back.repository.pacientes.FichaMedicaRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ContenidoMedicoService {
    private final ContenidoMedicoRepository contenidoMedicoRepository;
    private final FichaMedicaRepository fichaMedicaRepository;

    public ContenidoMedicoService(ContenidoMedicoRepository contenidoMedicoRepository, FichaMedicaRepository fichaMedicaRepository) {
        this.contenidoMedicoRepository = contenidoMedicoRepository;
        this.fichaMedicaRepository = fichaMedicaRepository;
    }

    public List<ContenidoMedicoEntity> getAllContenidosMedicos() {
        return contenidoMedicoRepository.findAll();
    }

    public ContenidoMedicoEntity getContenidoMedicoById(UUID id) {
        return contenidoMedicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenido médico no encontrado"));
    }

    public ContenidoMedicoEntity createContenidoMedico(CreateContenidoMedicoDTO dto) {
        if (!fichaMedicaRepository.existsById(dto.fichaId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ficha médica asociada no existe");
        }
        ContenidoMedicoEntity entity = ContenidoMedicoEntity.builder()
                .fichaId(dto.fichaId)
                .tipoContenido(dto.tipoContenido)
                .url(dto.url)
                .creadoEn(LocalDateTime.now())
                .build();
        return contenidoMedicoRepository.save(entity);
    }

    public ContenidoMedicoEntity updateContenidoMedico(UUID id, CreateContenidoMedicoDTO dto) {
        ContenidoMedicoEntity entity = contenidoMedicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenido médico no encontrado"));
        if (dto.fichaId != null) {
            if (!fichaMedicaRepository.existsById(dto.fichaId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ficha médica asociada no existe");
            }
            entity.setFichaId(dto.fichaId);
        }
        if (dto.tipoContenido != null) entity.setTipoContenido(dto.tipoContenido);
        if (dto.url != null) entity.setUrl(dto.url);
        return contenidoMedicoRepository.save(entity);
    }

    public void deleteContenidoMedico(UUID id) {
        if (!contenidoMedicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenido médico no encontrado");
        }
        contenidoMedicoRepository.deleteById(id);
    }
}
