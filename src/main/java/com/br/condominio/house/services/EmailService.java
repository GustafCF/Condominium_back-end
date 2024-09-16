package com.br.condominio.house.services;

import org.springframework.stereotype.Service;

import com.br.condominio.house.repositories.EmailRepository;

@Service
public class EmailService {

    private EmailRepository emailRepository;
    private JavaMailSender emailSender;

}
