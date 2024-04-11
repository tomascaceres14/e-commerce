package com.tomasdev.akhanta.exceptions;

public class CustomerExistsException extends RuntimeException {
    public CustomerExistsException() {
        super("El usuario ingresado ya existe");
    }
}
