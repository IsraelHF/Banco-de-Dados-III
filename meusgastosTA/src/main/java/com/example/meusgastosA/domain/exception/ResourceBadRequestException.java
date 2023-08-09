package com.example.meusgastosA.domain.exception;

public class ResourceBadRequestException extends RuntimeException {
    public ResourceBadRequestException(String mensagem) {
        super(mensagem);
    }

}
