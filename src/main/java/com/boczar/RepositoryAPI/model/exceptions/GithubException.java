package com.boczar.RepositoryAPI.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class GithubException extends RuntimeException {
    @Getter
    private final HttpStatus status;
    private final String message;

    public GithubException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

