package com.br.condominio.house.services.exceptions;

public class ResourceApException extends RuntimeException {

    public ResourceApException(int ap){
        super("O apartamento " + ap + " não foi encontrado");
    }

}
