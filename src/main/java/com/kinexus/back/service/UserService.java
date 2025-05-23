package com.kinexus.back.service;

import com.kinexus.back.dto.CreateUserDTO;
import com.kinexus.back.model.UserEntity;
import com.kinexus.back.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    public UserEntity updateUser(UUID id, CreateUserDTO dto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (dto.nombre != null) user.setNombre(dto.nombre);
        if (dto.email != null) user.setEmail(dto.email);
        if (dto.password != null) user.setPassword(dto.password);
        if (dto.tipoUsuario != null) user.setTipoUsuario(dto.tipoUsuario);
        return userRepository.save(user);
    }
}
