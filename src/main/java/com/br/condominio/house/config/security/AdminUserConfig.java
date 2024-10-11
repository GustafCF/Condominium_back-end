package com.br.condominio.house.config.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.FunctionaryRepository;
import com.br.condominio.house.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Configuration
@Profile("test")
public class AdminUserConfig implements CommandLineRunner {

    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final FunctionaryRepository functionaryRepository;

    public AdminUserConfig(RoleRepository roleRepository, FunctionaryRepository functionaryRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.functionaryRepository = functionaryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // roleRepository.deleteAll();
        // RoleModel r1 = new RoleModel(1L, "admin");
        // RoleModel r2 = new RoleModel(2L, "basic");
        // roleRepository.saveAll(Arrays.asList(r1, r2));

        var roleAdmin = roleRepository.findByName(RoleModel.Values.ADMIN.name());

        var userAdmin = functionaryRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new FunctionaryModel();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.getLs_roles().add(roleAdmin);
                    functionaryRepository.save(user);
                    System.out.println("Create admin");
                }
        );
    }

}
