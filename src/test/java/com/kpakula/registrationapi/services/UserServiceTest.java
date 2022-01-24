package com.kpakula.registrationapi.services;

import com.kpakula.registrationapi.dto.UserDTO;
import com.kpakula.registrationapi.exceptions.UserExistsException;
import com.kpakula.registrationapi.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void canSaveUserIntoDatabase() {
        // given
        UserDTO userDTO = new UserDTO("username", "password");

        // when
        User user = userService.save(userDTO);

        // then
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("username", user.getUsername());
        Assertions.assertNotEquals("password", user.getPassword());
    }

    @Test
    public void throwsExceptionWhenUserExists() {
        // given
        UserDTO userDTO1 = new UserDTO("username", "password1");
        UserDTO userDTO2 = new UserDTO("username", "password2");

        // when
        userService.save(userDTO1);
        Exception exception = Assertions.assertThrows(UserExistsException.class, () -> {
            userService.save(userDTO2);
        });

        Assertions.assertTrue(exception.getMessage().contains("exists in database"));
    }

    @Test
    public void canSaveMultipleUsersIntoDatabase() {
        // given
        UserDTO userDTO1 = new UserDTO("username1", "password");
        UserDTO userDTO2 = new UserDTO("username2", "password");

        // when
        User user1 = userService.save(userDTO1);
        User user2 = userService.save(userDTO2);

        // then
        Assertions.assertEquals(1, user1.getId());
        Assertions.assertEquals(2, user2.getId());
    }

}
