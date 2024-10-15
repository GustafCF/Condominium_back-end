package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.ApartmentRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.RoleRepository;
import com.br.condominio.house.services.exceptions.DatabaseException;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;
import com.br.condominio.house.services.exceptions.UnauthorizedException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ResidentService {

    private final ResidentRepository repository;
    private final ApartmentRepository apartmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ResidentService(ResidentRepository repository, ApartmentRepository apartmentRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.apartmentRepository = apartmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<ResidentModel> findAll() {
        return repository.findAll();
    }

    public ResidentModel findById(Long id) {
        Optional<ResidentModel> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Optional<ResidentModel> findByName(String name) {
        return repository.findByResidentName(name);
    }

    public ResidentModel insert(int ap, ResidentModel entity) {
        ApartmentModel apart = apartmentRepository.findById(ap)
                .orElseThrow(() -> new ResourceNotFoundException(ap));

        var basicRole = roleRepository.findByName(RoleModel.Values.BASIC.name());

        ResidentModel resident = new ResidentModel();
        resident.getAp().add(apart);
        resident.setResidentName(entity.getResidentName());
        resident.setLastName(entity.getLastName());
        resident.setDataNascimento(entity.getDataNascimento());
        resident.setAge(entity.getAge());
        resident.setProprietario(entity.getProprietario());
        resident.setRg(entity.getRg());
        resident.setCpf(entity.getCpf());
        resident.setEmail(entity.getEmail());
        resident.setUsername(entity.getUsername());
        resident.setPassword(passwordEncoder.encode(entity.getPassword()));
        resident.getRoles().add(basicRole);

        return repository.save(resident);
    }

    public ResidentModel addAp(Long residentId, int apartmentId) {
        ResidentModel resident = repository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException(residentId));

        ApartmentModel apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException(apartmentId));

        resident.getAp().add(apartment);
        return repository.save(resident);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public ResidentModel update(Long id, ResidentModel entity, JwtAuthenticationToken token) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        
        if (entity.getUsername().equalsIgnoreCase(token.getName())) {
            ResidentModel obj = repository.getReferenceById(id);
            updateData(obj, entity);
            return repository.save(obj);
        } else {
            throw new UnauthorizedException("Não autorizado!");
        }
    }

    private void updateData(ResidentModel entity, ResidentModel obj) {
        entity.setResidentName(obj.getResidentName());
        entity.setLastName(obj.getLastName());
        entity.setDataNascimento(obj.getDataNascimento());
        entity.setAge(obj.getAge());
        entity.setProprietario(obj.getProprietario());
        entity.setRg(obj.getRg());
        entity.setCpf(obj.getCpf());
    }

}
