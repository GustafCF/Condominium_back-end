package com.br.condominio.house.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.condominio.house.models.ParkingModel;
import com.br.condominio.house.repositories.ParkingRepository;
import com.br.condominio.house.services.exceptions.ResourceNotFoundException;

@Service
public class ParkingService {

    private final ParkingRepository repository;

    public ParkingService(ParkingRepository repository) {
        this.repository = repository;
    }

    public List<ParkingModel> findAll() {
        return repository.findAll();
    }

    public ParkingModel findById(int id) {
        Optional<ParkingModel> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

}
