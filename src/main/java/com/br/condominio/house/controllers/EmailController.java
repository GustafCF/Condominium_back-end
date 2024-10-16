package com.br.condominio.house.controllers;

import java.net.URI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.EmailModel;
import com.br.condominio.house.models.dto.EmailDto;
import com.br.condominio.house.services.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping(value = "/sendin-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        service.sendEmail(emailModel);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(emailModel.getEmailId()).toUri();
        return ResponseEntity.created(uri).body(emailModel);
    }

}
