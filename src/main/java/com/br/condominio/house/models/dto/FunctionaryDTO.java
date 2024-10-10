package com.br.condominio.house.models.dto;

import jakarta.validation.constraints.NotBlank;

public record FunctionaryDTO(
        @NotBlank(message = "O nome de usuário não pode estar em branco")
        String username,
        @NotBlank(message = "A senha não pode estar em branco")
        String password) {

}
