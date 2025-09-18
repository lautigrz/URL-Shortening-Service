package com.shortening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String shortUrl) {
        super(String.format("No se encontró la URL con código: %s", shortUrl));
    }
}
