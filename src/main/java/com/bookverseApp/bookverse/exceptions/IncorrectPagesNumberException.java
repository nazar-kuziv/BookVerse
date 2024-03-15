package com.bookverseApp.bookverse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectPagesNumberException extends RuntimeException {
    public IncorrectPagesNumberException() {
        super("Invalid number of pages.");
    }
}
