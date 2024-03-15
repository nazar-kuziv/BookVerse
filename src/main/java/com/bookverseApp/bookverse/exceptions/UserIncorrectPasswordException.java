package com.bookverseApp.bookverse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UserIncorrectPasswordException extends RuntimeException {
    public UserIncorrectPasswordException() {
        super("Invalid password.");
    }
}

