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
        CondModel u3 = new CondModel(null, "Rafael", "Alves Franco", 2);
        CondModel u4 = new CondModel(null, "Tanjiro", "Kamado", 19);
        CondModel u5 = new CondModel(null, "Gai", "Maito", 35);
        CondModel u6 = new CondModel(null, "Maki", "Zenin", 25);
        CondModel u7 = new CondModel(null, "Gojo", "Satouro", 30);
        CondModel u8 = new CondModel(null, "Yuji", "Itadore", 18);
        CondModel u9 = new CondModel(null, "Luffy", "Monkey D.", 20);

        condRepository.saveAll(Arrays.asList(u1,u2,u3,u4,u5,u6,u7,u8,u9));
    }

}
