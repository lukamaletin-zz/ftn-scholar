package io.github.lukamaletin.ftnscholar.controller.exception;

import io.github.lukamaletin.ftnscholar.controller.exception.resolver.ExceptionResolver;

/**
 * Custom exception.
 * Gets mapped to {@link org.springframework.http.HttpStatus#FORBIDDEN} when caught in
 * {@link ExceptionResolver}.
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
