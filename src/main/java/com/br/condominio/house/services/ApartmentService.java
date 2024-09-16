package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.repositories.ApartmentRepository;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository repository;

    public List<ApartmentModel> findAll(){
        return repository.findAll();
    }

    public ApartmentModel finById(int id){
        Optional<ApartmentModel> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

}
