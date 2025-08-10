package com.kinexus.back.service.pacientes;

import com.kinexus.back.model.pacientes.BloqueHorarioEntity;
import com.kinexus.back.repository.pacientes.BloqueHorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BloqueHorarioService {
    private final BloqueHorarioRepository bloqueHorarioRepository;

    public BloqueHorarioService(BloqueHorarioRepository bloqueHorarioRepository) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
    }

    public List<BloqueHorarioEntity> getAll() {
        return bloqueHorarioRepository.findAll();
    }

    public BloqueHorarioEntity getById(UUID id) {
        return bloqueHorarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Bloque horario no encontrado"));
    }

    public BloqueHorarioEntity create(BloqueHorarioEntity bloque) {
        return bloqueHorarioRepository.save(bloque);
    }

    public BloqueHorarioEntity update(UUID id, BloqueHorarioEntity bloque) {
        BloqueHorarioEntity existing = getById(id);
        // Actualiza campos según necesidad
        return bloqueHorarioRepository.save(existing);
    }

    public void delete(UUID id) {
        bloqueHorarioRepository.deleteById(id);
    }
}
