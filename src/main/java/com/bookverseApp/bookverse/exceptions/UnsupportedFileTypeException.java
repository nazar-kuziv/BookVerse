package com.bookverseApp.bookverse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnsupportedFileTypeException extends RuntimeException {
    public UnsupportedFileTypeException() {
        super("This file type is not supported.");
    }
}
