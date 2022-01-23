package com.kpakula.registrationapi.controllers;

import com.kpakula.registrationapi.DTO.UserDTO;
import com.kpakula.registrationapi.exceptions.UserExistsException;
import com.kpakula.registrationapi.models.User;
import com.kpakula.registrationapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO user) {
        User createdUser = userService.save(user);

        System.out.println(createdUser.getUsername());
        System.out.println(createdUser.getPassword());
        System.out.println(createdUser.getId());

        return ResponseEntity.ok(createdUser);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> handleUserExistsException(UserExistsException ex) {
        return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
    }
}
