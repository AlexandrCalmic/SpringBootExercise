package com.koerber.supplychain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ArticleNotFoundException extends ResponseStatusException {
    public ArticleNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
