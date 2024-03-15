package com.bookverseApp.bookverse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnavailableLoginException extends RuntimeException {
    public UnavailableLoginException() {
        super("This login is already taken.");
    }
}

