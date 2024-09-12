package com.br.condominio.house.services.exceptions;

public class ResourceNameException extends RuntimeException {

    public ResourceNameException(String message){
        super("O nome " + message + " , não foi encontrado");
    }

}
