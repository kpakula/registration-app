package com.kpakula.registrationapi.services;

import com.kpakula.registrationapi.DTO.UserDTO;
import com.kpakula.registrationapi.exceptions.UserExistsException;
import com.kpakula.registrationapi.models.User;
import com.kpakula.registrationapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserDTO userDTO) {
        User currentUser = new User(userDTO.getUsername(), userDTO.getPassword());
        boolean exists = userRepository.existsUserByUsername(userDTO.getUsername());

        if (!exists) return userRepository.save(currentUser);
        else throw new UserExistsException(currentUser.getUsername());
    }
}
