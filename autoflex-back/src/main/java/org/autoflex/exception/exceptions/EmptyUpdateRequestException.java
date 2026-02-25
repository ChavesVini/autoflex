package org.autoflex.exception.exceptions;

public class EmptyUpdateRequestException extends RuntimeException {
    public EmptyUpdateRequestException(String message) {
        super(message);
    }
}