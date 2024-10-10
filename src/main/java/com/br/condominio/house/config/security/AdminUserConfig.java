package com.br.condominio.house.config.security;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Configuration
@Profile("test")
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private ResidentRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, ResidentRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new ResidentModel();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.getRoles().add(roleAdmin);
                    userRepository.save(user);
                    System.out.println("Create admin");
                }
        );
    }

}
