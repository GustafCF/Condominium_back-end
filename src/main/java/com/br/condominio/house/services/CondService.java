package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.CondModel;
import com.br.condominio.house.repositories.CondRepository;
import com.br.condominio.house.services.exceptions.DatabaseException;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CondService {

    @Autowired
    private CondRepository repository;

    public List<CondModel> findAll(){
        return repository.findAll();
    }
    
    public CondModel findById(UUID id){
        Optional<CondModel> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public void delete(UUID id){
        try{
            repository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }    
    }

    public CondModel insert(CondModel entity){
        return repository.save(entity);
    }

    public CondModel update(UUID id, CondModel obj){
        try{
            CondModel entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
        
    }

    private void updateData(CondModel obj, CondModel entity){
        entity.setName(obj.getName());
        entity.setSurname(obj.getSurname());
    }

}
