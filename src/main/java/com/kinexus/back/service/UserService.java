package com.kinexus.back.service;

import com.kinexus.back.model.UserEntity;
import com.kinexus.back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
