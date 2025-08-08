package com.kinexus.back.service;

import com.kinexus.back.dto.CreateFichaMedicaDTO;
import com.kinexus.back.model.FichaMedicaEntity;
import com.kinexus.back.repository.FichaMedicaRepository;
import com.kinexus.back.repository.UserRepository;

import lombok.Data;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Data
@Service
public class FichaMedicaService {

    private final FichaMedicaRepository fichaMedicaRepository;
    private final UserRepository userRepository;

    public FichaMedicaService(FichaMedicaRepository fichaMedicaRepository, UserRepository userRepository) {
        this.fichaMedicaRepository = fichaMedicaRepository;
        this.userRepository = userRepository;
    }

    public List<FichaMedicaEntity> getAllFichasMedicas() {
        return fichaMedicaRepository.findAll();
    }

    public FichaMedicaEntity getFichaMedicaById(UUID id) {
        return fichaMedicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ficha médica no encontrada"));
    }

    public FichaMedicaEntity createFichaMedica(FichaMedicaEntity fichaMedica) {
        return fichaMedicaRepository.save(fichaMedica);
    }

    public FichaMedicaEntity updateFichaMedica(UUID id, CreateFichaMedicaDTO fichaMedicaDTO) {
        FichaMedicaEntity existingFicha = fichaMedicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ficha médica no encontrada"));
        if (fichaMedicaDTO.pacienteId != null) {
            if (!userRepository.existsById(fichaMedicaDTO.pacienteId)) {
                throw new RuntimeException("El paciente con ID " + fichaMedicaDTO.pacienteId + " no existe");
            }
            existingFicha.setPacienteId(fichaMedicaDTO.pacienteId);
        }
        if (fichaMedicaDTO.descripcion != null) existingFicha.setDescripcion(fichaMedicaDTO.descripcion);
        return fichaMedicaRepository.save(existingFicha);
    }

    public void deleteFichaMedica(UUID id) {
        if (!fichaMedicaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ficha médica no encontrada");
        }
        fichaMedicaRepository.deleteById(id);
    }
}
