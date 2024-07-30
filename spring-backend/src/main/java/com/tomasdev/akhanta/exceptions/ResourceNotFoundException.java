package com.tomasdev.akhanta.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Lo sentimos, recurso no encontrado.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
