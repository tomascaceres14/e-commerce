package com.tomasdev.akhanta.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("No tiene los permisos necesarios.");
    }
}
