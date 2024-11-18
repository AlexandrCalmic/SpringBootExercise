package com.koerber.supplychain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ArticleAlreadyExistsException extends ResponseStatusException {
    public ArticleAlreadyExistsException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}