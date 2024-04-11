package com.tomasdev.akhanta.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(STR."\{message} no encontrado.");
    }
}
