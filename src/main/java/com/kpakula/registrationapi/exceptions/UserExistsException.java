package com.kpakula.registrationapi.exceptions;

public class UserExistsException extends IllegalArgumentException {
    public static final String USER_EXISTS = "User %s exists in database";
    public UserExistsException(String username) {
        super(String.format(USER_EXISTS, username));
    }
}
