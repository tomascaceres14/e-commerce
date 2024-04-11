package com.tomasdev.akhanta.exceptions;

public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException() {
        super("La contraseña es incorrecta.");
    }
}
