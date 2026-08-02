package br.com.fiap.users_spring_security.exceptions;

public class GerarTokenException extends RuntimeException {
    public GerarTokenException(String message) {
        super(message);
    }
}
