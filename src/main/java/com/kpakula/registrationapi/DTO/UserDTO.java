package com.kpakula.registrationapi.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @NotNull
    @Size(min = 5, max = 60)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String username;

    @NotNull
    @Size(min = 8, max = 60)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "The password does not meet the criteria. Should contain at least one lowercase, uppercase, digit and symbol")
    private String password;
}
