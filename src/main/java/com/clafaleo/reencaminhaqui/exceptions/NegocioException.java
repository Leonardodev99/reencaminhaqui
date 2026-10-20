package com.clafaleo.reencaminhaqui.exceptions;

import org.springframework.http.HttpStatus;

public class NegocioException extends RuntimeException {
    private final HttpStatus status;

    private NegocioException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }

    public static NegocioException naoEncontrado(String msg) { return new NegocioException(HttpStatus.NOT_FOUND, msg); }
    public static NegocioException conflito(String msg)      { return new NegocioException(HttpStatus.CONFLICT, msg); }
    public static NegocioException invalido(String msg)      { return new NegocioException(HttpStatus.BAD_REQUEST, msg); }
}