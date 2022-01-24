package com.kpakula.registrationapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    @Size(min = 5, max = 60)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "The username should contains only letters and numbers")
    private String username;

    @NotNull
    @Size(min = 8, max = 60)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).*$",
            message = "The password does not meet the criteria. Should contain at least one lowercase, uppercase, digit and symbol")
    private String password;
}
