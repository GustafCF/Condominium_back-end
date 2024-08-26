package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.ResidentRespository;
import com.br.condominio.house.services.exceptions.DatabaseException;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResidentService {

    @Autowired
    private ResidentRespository repository;

    public List<ResidentModel> findAll(){
        return repository.findAll();
    }

    public ResidentModel findById(UUID id){
        Optional<ResidentModel> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public List<ResidentModel> findByName(String name) {
        return repository.findByResidentName(name);
    }

    public ResidentModel insert (ResidentModel entity){
        return repository.save(entity);
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

    public ResidentModel update(UUID id, ResidentModel entity){
        try{
            ResidentModel obj = repository.getReferenceById(id);
            updateData(obj, entity);
            return repository.save(obj);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(ResidentModel entity, ResidentModel obj){
        entity.setResidentName(obj.getResidentName());
        entity.setLastName(obj.getLastName());
        entity.setDataNascimento(obj.getDataNascimento());
        entity.setAge(obj.getAge());
        entity.setProprietario(obj.getProprietario());
        entity.setRg(obj.getRg());
        entity.setCpf(obj.getCpf());
    }

}
