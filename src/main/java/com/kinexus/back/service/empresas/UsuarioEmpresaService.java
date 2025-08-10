package com.kinexus.back.service.empresas;

import com.kinexus.back.model.empresas.UsuarioEmpresaEntity;
import com.kinexus.back.dto.empresas.CreateUsuarioEmpresaDTO;
import com.kinexus.back.repository.empresas.UsuarioEmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioEmpresaService {
    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    public UsuarioEmpresaService(UsuarioEmpresaRepository usuarioEmpresaRepository) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
    }

    public List<UsuarioEmpresaEntity> getAllUsuariosEmpresa() {
        return usuarioEmpresaRepository.findAll();
    }

    public UsuarioEmpresaEntity getUsuarioEmpresaById(UUID id) {
        return usuarioEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioEmpresa no encontrado"));
    }

    public UsuarioEmpresaEntity createUsuarioEmpresa(CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity usuario = UsuarioEmpresaEntity.builder()
                .nombre(dto.nombre)
                .edad(dto.edad)
                .genero(dto.genero)
                .fechaNacimiento(dto.fechaNacimiento)
                .cargo(dto.cargo)
                .discapacidad(dto.discapacidad)
                .build();
        return usuarioEmpresaRepository.save(usuario);
    }

    public UsuarioEmpresaEntity updateUsuarioEmpresa(UUID id, CreateUsuarioEmpresaDTO dto) {
        UsuarioEmpresaEntity usuario = getUsuarioEmpresaById(id);
        usuario.setNombre(dto.nombre);
        usuario.setEdad(dto.edad);
        usuario.setGenero(dto.genero);
        usuario.setFechaNacimiento(dto.fechaNacimiento);
        usuario.setCargo(dto.cargo);
        usuario.setDiscapacidad(dto.discapacidad);
        return usuarioEmpresaRepository.save(usuario);
    }

    public void deleteUsuarioEmpresa(UUID id) {
        if (!usuarioEmpresaRepository.existsById(id)) {
            throw new RuntimeException("UsuarioEmpresa no encontrado");
        }
        usuarioEmpresaRepository.deleteById(id);
    }
}
