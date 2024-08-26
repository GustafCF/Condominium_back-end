package com.br.condominio.house.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.ResidentRespository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ResidentRespository residentRespository;

    @Override
    public void run(String... args) throws Exception {

        ResidentModel r1 = new ResidentModel(null, "Mariana Huyla A M", LocalDate.of(2002, 8, 30), 21, true, "123456", "07332605198");

        residentRespository.save(r1);
      
    }

}
