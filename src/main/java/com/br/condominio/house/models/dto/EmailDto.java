package com.br.condominio.house.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
        @NotBlank
        String ownerRef,
        @NotBlank
        @Email
        String emailFrom,
        @NotBlank
        String emailTo,
        @NotBlank
        String subject,
        @NotBlank
        String text) {

}
