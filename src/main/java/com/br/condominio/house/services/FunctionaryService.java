package com.br.condominio.house.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.FunctionaryRepository;
import com.br.condominio.house.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class FunctionaryService {

    private FunctionaryRepository repository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public FunctionaryService(FunctionaryRepository repository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public FunctionaryModel registrar(FunctionaryModel entity) {
        var roleAdmin = roleRepository.findByName(RoleModel.Values.ADMIN.name());

        FunctionaryModel functionary = new FunctionaryModel();
        functionary.setName(entity.getName());
        functionary.setLastName(entity.getLastName());
        functionary.setEmail(entity.getEmail());
        functionary.setRg(entity.getRg());
        functionary.setCpf(entity.getCpf());
        functionary.setCep(entity.getCep());
        functionary.setAddress(entity.getAddress());
        functionary.setUsername(entity.getUsername());
        functionary.setPassword(passwordEncoder.encode(entity.getPassword()));
        functionary.getLs_roles().add(roleAdmin);

        return repository.save(functionary);
    }

}
