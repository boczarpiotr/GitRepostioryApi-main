package com.boczar.RepositoryAPI.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(Map.of(
                        "status", ex.getStatus().value(),
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(GithubException.class)
    public ResponseEntity<Map<String, Object>> handleGithub(GithubException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(Map.of(
                        "status", ex.getStatus().value(),
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception ex) {
        return ResponseEntity.status(500)
                .body(Map.of(
                        "status", 500,
                        "message", "Internal Server Error")
                );
    }
}