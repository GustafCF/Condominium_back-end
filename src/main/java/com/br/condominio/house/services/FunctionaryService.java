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

import jakarta.persistence.EntityNotFoundException;
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

    public FunctionaryModel update(Long id, FunctionaryModel obj) {
        try {
            var entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(FunctionaryModel entity, FunctionaryModel obj) {
        entity.setName(obj.getName());
        entity.setLastName(obj.getLastName());
        entity.setEmail(obj.getEmail());
        entity.setRg(obj.getRg());
        entity.setCpf(obj.getCpf());
        entity.setCep(obj.getCep());
        entity.setAddress(obj.getAddress());
        entity.setUsername(obj.getUsername());
    }
}
