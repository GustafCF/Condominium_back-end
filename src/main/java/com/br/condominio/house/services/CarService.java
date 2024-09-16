package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.CarRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.services.exceptions.DatabaseException;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    @Autowired
    private ResidentRepository residentRepository;

    public List<CarModel> findAll(){
       return repository.findAll();
    }

    public CarModel findById(Long id){
        Optional<CarModel> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    @Transactional
    public CarModel registerCarForResident(Long residentId, CarModel carModel) { 
        ResidentModel resident = residentRepository.findById(residentId)
            .orElseThrow(() -> new RuntimeException("Resident not found with id: " + residentId));
        carModel.setOwner(resident);
        return repository.save(carModel);
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }  
    }

    // public CarModel update(Long id, CarModel obj){
    //     try{
    //         CarModel entity = repository.getReferenceById(id);
    //         updateData(entity, obj);
    //         return repository.save(entity); 
    //     } catch(EntityNotFoundException e){
    //         throw new ResourceNotFoundException(id);
    //     }
    // }

    // private void updateData(CarModel obj, CarModel entity){
    //     entity.setModel(obj.getModel());
    //     entity.setBrand(obj.getBrand());
    //     entity.setType(obj.getType());
    //     entity.setPlate(obj.getPlate());
    //     entity.setAno(obj.getAno());
    // }

}
