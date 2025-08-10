package com.kinexus.back.service.pacientes;

import com.kinexus.back.dto.pacientes.CreateCitaDTO;
import com.kinexus.back.model.pacientes.CitaEntity;
import com.kinexus.back.repository.UserRepository;
import com.kinexus.back.repository.pacientes.CitaRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CitaService {
    private final CitaRepository citaRepository;
    private final UserRepository userRepository;

    public CitaService(CitaRepository citaRepository, UserRepository userRepository) {
        this.citaRepository = citaRepository;
        this.userRepository = userRepository;
    }

    public List<CitaEntity> getAllCitas() {
        return citaRepository.findAll();
    }

    public CitaEntity getCitaById(UUID id) {
        return citaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
    }

    public CitaEntity createCita(CreateCitaDTO dto) {
        if (dto.pacienteId != null && !userRepository.existsById(dto.pacienteId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente no existe");
        }
        if (!userRepository.existsById(dto.adminId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administrador no existe");
        }
        CitaEntity entity = CitaEntity.builder()
                .pacienteId(dto.pacienteId)
                .adminId(dto.adminId)
                .fechaHora(dto.fechaHora)
                .estado(dto.estado != null ? dto.estado : "disponible")
                .creadoEn(LocalDateTime.now())
                .build();
        return citaRepository.save(entity);
    }

    public CitaEntity updateCita(UUID id, CreateCitaDTO dto) {
        CitaEntity entity = citaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
        if (dto.pacienteId != null) {
            if (!userRepository.existsById(dto.pacienteId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente no existe");
            }
            entity.setPacienteId(dto.pacienteId);
        }
        if (dto.adminId != null) {
            if (!userRepository.existsById(dto.adminId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administrador no existe");
            }
            entity.setAdminId(dto.adminId);
        }
        if (dto.fechaHora != null) entity.setFechaHora(dto.fechaHora);
        if (dto.estado != null) entity.setEstado(dto.estado);
        return citaRepository.save(entity);
    }

    public void deleteCita(UUID id) {
        if (!citaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }
}
