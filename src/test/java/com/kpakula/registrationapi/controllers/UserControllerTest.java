package com.kpakula.registrationapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpakula.registrationapi.dto.UserDTO;
import com.kpakula.registrationapi.exceptions.UserExistsException;
import com.kpakula.registrationapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void shouldProperlyAddNewUser() throws Exception {
        UserDTO userDTO = new UserDTO("username", "passwordA1@");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowErrorWhenUserExists() throws Exception {
        UserDTO userDTO = new UserDTO("username", "passwordA1@");
        Mockito.when(userService.save(Mockito.any())).thenThrow(new UserExistsException("username"));

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldThrowErrorWhenUsernameIsTooShort() throws Exception {
        UserDTO userDTO = new UserDTO("abcd", "passwordA1@");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"username\":\"size must be between 5 and 60\"}")).andReturn();
    }

    @Test
    public void shouldThrowErrorWhenPasswordIsTooShort() throws Exception {
        UserDTO userDTO = new UserDTO("abcde", "pA1#");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"password\":\"size must be between 8 and 60\"}")).andReturn();
    }

    @Test
    public void shouldThrowErrorWhenUsernameHasIncorrectFormat() throws Exception {
        UserDTO userDTO = new UserDTO("abcde1@", "passwordA1@");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"username\":\"The username should contains only letters and numbers\"}")).andReturn();
    }

    @Test
    public void shouldThrowErrorWhenPasswordHasIncorrectFormat() throws Exception {
        UserDTO userDTO = new UserDTO("abcde1", "passwordA1");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"password\":\"The password does not meet the criteria. Should contain at least one lowercase, uppercase, digit and symbol\"}")).andReturn();
    }

    @Test
    public void shouldThrowErrorWhenUsernameAndPasswordAreIncorrect() throws Exception {
        UserDTO userDTO = new UserDTO("abcde1@", "passwordA1");
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"password\":\"The password does not meet the criteria. Should contain at least one lowercase, uppercase, digit and symbol\",\"username\":\"The username should contains only letters and numbers\"}")).andReturn();
    }
}
