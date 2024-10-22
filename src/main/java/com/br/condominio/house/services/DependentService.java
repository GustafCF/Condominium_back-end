package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.DependentRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

@Service
public class DependentService {

    @Autowired
    private DependentRepository repository;

    @Autowired
    private ResidentRepository residentRepository;

    public List<DependentModel> findAll() {
        return repository.findAll();
    }

    public DependentModel findById(Long id) {
        Optional<DependentModel> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<DependentModel> findByName(String name) {
        return repository.findByName(name);
    }

    public DependentModel insert(Long id, DependentModel entity) {
        ResidentModel r = residentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        entity.getFathers().add(r);
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public DependentModel update(Long id, DependentModel entity) {
        DependentModel obj = repository.getReferenceById(id);
        updateData(obj, entity);
        return repository.save(obj);
    }

    private void updateData(DependentModel obj, DependentModel entity) {
        if (obj.getName() != null && !obj.getName().isEmpty()) {
            entity.setName(obj.getName());
        }
        if (obj.getLastName() != null && !obj.getLastName().isEmpty()) {
            entity.setLastName(obj.getLastName());
        }
        if (obj.getDateBirth() != null) {
            entity.setDateBirth(obj.getDateBirth());
        }
        if(obj.getAge() != null){
            entity.setAge(obj.getAge());
        }
    }

}
