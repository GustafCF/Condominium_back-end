package com.br.condominio.house.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.br.condominio.house.models.CondModel;
import com.br.condominio.house.repositories.CondRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private CondRepository condRepository;

    @Override
    public void run(String... args) throws Exception {

        CondModel u1 = new CondModel(null, "Gustavo", "Cesar Franco", 23);
        CondModel u2 = new CondModel(null, "Mariana", "Huyla Alves Miranda", 22);

        condRepository.saveAll(Arrays.asList(u1,u2));
    }

}
