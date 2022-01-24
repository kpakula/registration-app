package com.kpakula.registrationapi.services;

import com.kpakula.registrationapi.dto.UserDTO;
import com.kpakula.registrationapi.exceptions.UserExistsException;
import com.kpakula.registrationapi.models.User;
import com.kpakula.registrationapi.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(UserDTO userDTO) {
        User currentUser = new User(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()));
        boolean exists = userRepository.existsUserByUsername(userDTO.getUsername());

        if (exists) throw new UserExistsException(currentUser.getUsername());
        return userRepository.save(currentUser);
    }
}
