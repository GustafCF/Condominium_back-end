package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.models.EmailModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.repositories.ApartmentRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.RoleRepository;
import com.br.condominio.house.services.exceptions.DatabaseException;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;
import com.br.condominio.house.services.exceptions.UnauthorizedException;

import jakarta.transaction.Transactional;

@Service
public class ResidentService {

    private final ResidentRepository repository;
    private final ApartmentRepository apartmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public ResidentService(ResidentRepository repository, ApartmentRepository apartmentRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, EmailService emailService) {
        this.repository = repository;
        this.apartmentRepository = apartmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
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

        if (!entity.getEmail().isEmpty()) {
            EmailModel email = new EmailModel();
            email.setEmailFrom("cond_back@email.com");
            email.setEmailTo(entity.getEmail());
            email.setSubject("Bem-vindo ao condomínio!");
            email.setText("Olá " + entity.getResidentName() + ",\n\nBEm-vindo ao nosso Condomínio. É um prazer tê-lo conosco." + "\n\nAqui está seu nome de usuário: " + entity.getUsername() + ", e sua senha: " + entity.getPassword()
                    + "\n\nRecomendamos que os troque assim que possível! Por motivos de segurança");
            emailService.sendEmail(email);
        }

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
        if (obj.getResidentName() != null && !obj.getResidentName().isEmpty()) {
            entity.setResidentName(obj.getResidentName());
        }
        if (obj.getLastName() != null && !obj.getLastName().isEmpty()) {
            entity.setLastName(obj.getLastName());
        }
        if (obj.getDataNascimento() != null) {
            entity.setDataNascimento(obj.getDataNascimento());
        }
        if (obj.getAge() != null) {
            entity.setAge(obj.getAge());
        }
        if (obj.getProprietario() != null) {
            entity.setProprietario(obj.getProprietario());
        }
        if (obj.getRg() != null && !obj.getRg().isEmpty() && !obj.getRg().isBlank()) {
            entity.setRg(obj.getRg());
        }
        if (obj.getCpf() != null && !obj.getCpf().isEmpty() && !obj.getCpf().isBlank()) {
            entity.setCpf(obj.getCpf());
        }
        if (obj.getEmail() != null && !obj.getEmail().isEmpty()) {
            obj.setEmail(obj.getEmail());
        }
        if (obj.getUsername() != null && !obj.getUsername().isEmpty() && !obj.getUsername().isBlank()) {
            obj.setUsername(obj.getUsername());
        }

    }

}
