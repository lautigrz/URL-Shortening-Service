package com.shortening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUrlNotFoundException(final UrlNotFoundException e) {

        Map<String, Object> body = new HashMap<String, Object>();

        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
