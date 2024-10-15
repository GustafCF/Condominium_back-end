package com.br.condominio.house.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.FunctionaryRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.RoleRepository;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;
import com.br.condominio.house.services.exceptions.UnauthorizedException;

import jakarta.transaction.Transactional;

@Service
public class FunctionaryService {

    private final FunctionaryRepository repository;
    private final ResidentRepository residentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public FunctionaryService(FunctionaryRepository repository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, ResidentRepository residentRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.residentRepository = residentRepository;
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

    public void delete(Long id, JwtAuthenticationToken token) {
        var func = repository.findById(Long.valueOf(token.getName()));

        var isAdmin = func.get().getLs_roles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(RoleModel.Values.ADMIN.name()));

        if (isAdmin) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else if (residentRepository.existsById(id)) {
                residentRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException(id);
            }

        } else {
            throw new UnauthorizedException("Usuário não tem permissão para deletar este registro.");
        }
    }

    @Transactional
    public FunctionaryModel update(Long id, FunctionaryModel obj, JwtAuthenticationToken token) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        var entity = repository.getReferenceById(id);
        if (entity.getId().equals(Long.valueOf(token.getName()))) {
            updateData(entity, obj);
            return repository.save(entity);
        } else {
            throw new UnauthorizedException("Você não está authorizado");
        }
    }

    private void updateData(FunctionaryModel entity, FunctionaryModel obj) {
        if (obj.getName() != null && !obj.getName().isEmpty()) {
            entity.setName(obj.getName());
        }
        if (obj.getLastName() != null && !obj.getLastName().isEmpty()) {
            entity.setLastName(obj.getLastName());
        }
        if (obj.getEmail() != null && !obj.getEmail().isEmpty()) {
            entity.setEmail(obj.getEmail());
        }
        if (obj.getCep() != null && !obj.getCep().isEmpty()) {
            entity.setCep(obj.getCep());
        }
        if (obj.getAddress() != null && !obj.getAddress().isEmpty()) {
            entity.setAddress(obj.getAddress());
        }
        if (obj.getUsername() != null && !obj.getUsername().isEmpty()) {
            entity.setUsername(obj.getUsername());
        }
    }

}
