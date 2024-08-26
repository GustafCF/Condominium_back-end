package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.ResidentRespository;

@Service
public class ResidentService {

    @Autowired
    private ResidentRespository repository;

    public List<ResidentModel> findAll(){
        return repository.findAll();
    }

    public ResidentModel findById(UUID id){
        Optional<ResidentModel> obj = repository.findById(id);
        return obj.get();
    }

    public List<ResidentModel> findByName(String name) {
        return repository.findByResidentName(name);
    }



}
