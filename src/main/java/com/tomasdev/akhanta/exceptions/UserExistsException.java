package com.tomasdev.akhanta.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("El usuario ingresado ya existe");
    }
}
