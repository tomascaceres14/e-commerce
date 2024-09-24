package com.tomasdev.akhanta.users;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("La dirección de correo ingresada ya tiene un usuario asignado.");
    }
}
