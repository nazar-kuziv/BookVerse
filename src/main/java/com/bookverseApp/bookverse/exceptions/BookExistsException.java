package com.bookverseApp.bookverse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookExistsException extends RuntimeException {
    public BookExistsException(String title) {
        super("The book with the title: " + title + " already exists.");
    }
}
