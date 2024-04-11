package com.tomasdev.akhanta.exceptions;

public class EmailValidationException extends RuntimeException{

    public EmailValidationException() {
        super("El email no tiene el formato requerido.");
    }
}
