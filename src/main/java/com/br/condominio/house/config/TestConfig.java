package com.br.condominio.house.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.ResidentRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public void run(String... args) throws Exception {

        ResidentModel r1 = new ResidentModel(null, "Mariana","Huyla A M R", LocalDate.of(2002, 8, 30), 21, true, "123456", "07332605198");
        ResidentModel r2 = new ResidentModel(null, "Gustavo", "Cesar Franco", LocalDate.of(2001, 2, 1), 23, true, "123456", "07332605198");
        ResidentModel r3 = new ResidentModel(null, "Verônica", "Alves Franco", LocalDate.of(2024, 12, 25), 0, true, "123456", "07332605198");
        ResidentModel r4 =  new ResidentModel(null, "Monkey", "D Luffy", LocalDate.of(1997, 3, 10), 27, false, "123456", "07332605198");
        ResidentModel r5 = new ResidentModel(null, "Tanjiro", "Kamado", LocalDate.of(2000, 8, 10), 24 ,false, "12345", "07332605198");

        residentRepository.saveAll(Arrays.asList(r1,r2,r3,r4,r5));
    }

}
