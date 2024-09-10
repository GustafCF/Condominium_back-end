package com.br.condominio.house.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.CarRepository;
import com.br.condominio.house.repositories.DependentRepository;
import com.br.condominio.house.repositories.ResidentRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DependentRepository dependentRepository;

    @Override
    public void run(String... args) throws Exception {
        
        dependentRepository.deleteAll();
        carRepository.deleteAll();
        residentRepository.deleteAll();

        ResidentModel r1 = new ResidentModel(null, "Mariana", "Huyla Alves Miranda Ribeiro", LocalDate.of(2002, 8, 30), 22 , true, "44453671172", "56048536151");
        ResidentModel r2 = new ResidentModel(null, "Gustavo", "Cesar Franco", LocalDate.of(2001, 2, 1), 23, true, "122560127", "50388175133");
        ResidentModel r3 = new ResidentModel(null, "Vitória", "Higino", LocalDate.of(2002, 9, 11), 22, true, "171928192", "39699335106");
        ResidentModel r4 = new ResidentModel(null, "Pedro", "Henrique Ramos Cardoso", LocalDate.of(2002, 1, 11), 22, true, "120850485", "44453671172");

        CarModel c1 = new CarModel(0L, "Audi", "A4", "Sedan", "placa-1", 2024, r1);
        CarModel c2 = new CarModel(0L, "Toyota", "Camry", "Sedan", "placa-2", 2023, r1);
        CarModel c3 = new CarModel(0L, "BMW", "X5", "SUV", "placa-3", 2022, r2);
        CarModel c4 = new CarModel(0L, "Tesla", "Model 3", "Hatchback elétrico", "placa-4", 2024, r4);

        residentRepository.saveAll(Arrays.asList(r1, r2, r3, r4));
        carRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

        DependentModel m1 = new DependentModel(null, "Verônica", "Alves Franco", LocalDate.of(2024, 12, 25), 0, Set.of(r1, r2));

        dependentRepository.save(m1);
    }

}