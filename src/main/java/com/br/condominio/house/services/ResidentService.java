package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.ApartmentRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.services.exceptions.DatabaseException;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResidentService {

    private ResidentRepository repository;
    private ApartmentRepository apartmentRepository;

    public ResidentService(ResidentRepository repository, ApartmentRepository apartmentRepository){
        this.repository = repository;
        this.apartmentRepository = apartmentRepository;
    }
    
    public List<ResidentModel> findAll(){
        return repository.findAll();
    }

    public ResidentModel findById(Long id){
        Optional<ResidentModel> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public List<ResidentModel> findByName(String name) {
        return repository.findByResidentName(name);
    }

    public ResidentModel insert(int ap, ResidentModel entity) {
        ApartmentModel apart = apartmentRepository.findById(ap)
            .orElseThrow(() -> new ResourceNotFoundException(ap));
        entity.getAp().add(apart);
        return repository.save(entity);
    }

    public ResidentModel addAp(Long residentId, int apartmentId) {
        ResidentModel resident = repository.findById(residentId)
            .orElseThrow(() -> new ResourceNotFoundException(residentId));
    
        ApartmentModel apartment = apartmentRepository.findById(apartmentId)
            .orElseThrow(() -> new ResourceNotFoundException(apartmentId));
    
        resident.getAp().add(apartment);
        return repository.save(resident);
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }    
    }

    public ResidentModel update(Long id, ResidentModel entity){
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
