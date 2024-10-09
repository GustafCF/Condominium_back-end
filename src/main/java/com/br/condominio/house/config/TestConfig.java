package com.br.condominio.house.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.models.ParkingModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.ApartmentRepository;
import com.br.condominio.house.repositories.CarRepository;
import com.br.condominio.house.repositories.DependentRepository;
import com.br.condominio.house.repositories.ParkingRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.RoleRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private ResidentRepository residentRepository;
    private CarRepository carRepository;
    private DependentRepository dependentRepository;
    private ApartmentRepository apartmentRepository;
    private ParkingRepository parkingRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TestConfig(ResidentRepository residentRepository, CarRepository carRepository, DependentRepository dependentRepository,
            ApartmentRepository apartmentRepository, ParkingRepository parkingRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.residentRepository = residentRepository;
        this.carRepository = carRepository;
        this.dependentRepository = dependentRepository;
        this.apartmentRepository = apartmentRepository;
        this.parkingRepository = parkingRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // parkingRepository.deleteAll();
        // carRepository.deleteAll();
        // dependentRepository.deleteAll();
        // residentRepository.deleteAll();
        // apartmentRepository.deleteAll();
        // ApartmentModel p1 = new ApartmentModel(101, "A");
        // ApartmentModel p2 = new ApartmentModel(102, "A");
        // ApartmentModel p3 = new ApartmentModel(103, "A");
        // ApartmentModel p4 = new ApartmentModel(104, "A");
        // ApartmentModel p5 = new ApartmentModel(105, "A");
        // ApartmentModel p6 = new ApartmentModel(106, "A");
        // ApartmentModel p7 = new ApartmentModel(201, "B");
        // ApartmentModel p8 = new ApartmentModel(202, "B");
        // ApartmentModel p9 = new ApartmentModel(203, "B");
        // ApartmentModel p10 = new ApartmentModel(204, "B");
        // ApartmentModel p11 = new ApartmentModel(205, "B");
        // ApartmentModel p12 = new ApartmentModel(206, "B");
        // apartmentRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
        // ParkingModel pk1 = new ParkingModel(1, p1);
        // ParkingModel pk2 = new ParkingModel(2, p2);
        // ParkingModel pk3 = new ParkingModel(3, p3);
        // ParkingModel pk4 = new ParkingModel(4, p4);
        // ParkingModel pk5 = new ParkingModel(5, p5);
        // ParkingModel pk6 = new ParkingModel(6, p6);
        // ParkingModel pk7 = new ParkingModel(7, p7);
        // ParkingModel pk8 = new ParkingModel(8, p8);
        // ParkingModel pk9 = new ParkingModel(9, p9);
        // ParkingModel pk10 = new ParkingModel(10, p10);
        // ParkingModel pk11 = new ParkingModel(11, p11);
        // ParkingModel pk12 = new ParkingModel(12, p12);
        // ParkingModel pk13 = new ParkingModel(13, p12);
        // parkingRepository.saveAll(Arrays.asList(pk1, pk2, pk3, pk4, pk5, pk6, pk7, pk8, pk9, pk10, pk11, pk12, pk13));
        // ResidentModel r1 = new ResidentModel(null, "Mariana", "Huyla Alves Miranda Ribeiro", LocalDate.of(2002, 8, 30), 22, true, "44453671172", "56048536151", "HuylaMary@gmail.com", "may", bCryptPasswordEncoder.encode("123"));
        // ResidentModel r2 = new ResidentModel(null, "Gustavo", "Cesar Franco", LocalDate.of(2001, 2, 1), 23, true, "122560127", "50388175133", "gustavocerro3@gmail.com", "gus", bCryptPasswordEncoder.encode("123"));
        // ResidentModel r3 = new ResidentModel(null, "Vitória", "Higino", LocalDate.of(2002, 9, 11), 22, true, "171928192", "39699335106", "vitória@gmail.com", "vi", bCryptPasswordEncoder.encode("123"));
        // ResidentModel r4 = new ResidentModel(null, "Pedro", "Henrique Ramos Cardoso", LocalDate.of(2002, 1, 11), 22, true, "120850485", "44453671172", "pedro@gmail.com", "pedro", bCryptPasswordEncoder.encode("123"));
        // residentRepository.saveAll(Arrays.asList(r1, r2, r3, r4));
        // var basicRole = roleRepository.findByName(RoleModel.Values.BASIC.name());
        // r1.getRoles().add(basicRole);
        // r2.getRoles().add(basicRole);
        // r3.getRoles().add(basicRole);
        // r4.getRoles().add(basicRole);
        // residentRepository.saveAll(Arrays.asList(r1, r2, r3, r4));
        // DependentModel m1 = new DependentModel(null, "Verônica", "Alves Franco", LocalDate.of(2024, 12, 25), 0);
        // dependentRepository.save(m1);
        // m1.getFathers().addAll(Arrays.asList(r1, r2));
        // dependentRepository.save(m1);
        // CarModel c1 = new CarModel(0L, "Audi", "A4", "Sedan", "placa-1", 2024, r1);
        // CarModel c2 = new CarModel(0L, "Toyota", "Camry", "Sedan", "placa-2", 2023, r1);
        // CarModel c3 = new CarModel(0L, "BMW", "X5", "SUV", "placa-3", 2022, r2);
        // CarModel c4 = new CarModel(0L, "Tesla", "Model 3", "Hatchback elétrico", "placa-4", 2024, r4);
        // carRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
        // r1.getAp().add(p12);
        // r2.getAp().add(p12);
        // r3.getAp().add(p2);
        // r4.getAp().add(p2);
        // residentRepository.saveAll(Arrays.asList(r1, r2, r3, r4));
        // m1.getAp_son().add(p12);
        // dependentRepository.save(m1);
    }

}
