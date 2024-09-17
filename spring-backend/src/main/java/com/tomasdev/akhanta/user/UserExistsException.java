package com.tomasdev.akhanta.user;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("La dirección de correo ingresada ya tiene un usuario asignado.");
    }
}
