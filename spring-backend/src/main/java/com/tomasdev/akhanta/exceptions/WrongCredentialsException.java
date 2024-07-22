package com.tomasdev.akhanta.exceptions;

public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException() {
        super("Credenciales incorrectas. Revisa las credenciales e intenta nuevamente.");
    }

    public WrongCredentialsException(String message) {
        super(message);
    }
}
