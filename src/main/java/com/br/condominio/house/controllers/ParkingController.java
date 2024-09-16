package com.br.condominio.house.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.ParkingModel;
import com.br.condominio.house.services.ParkingService;

@RestController
@RequestMapping(value = "/park")
public class ParkingController {

    @Autowired
    private ParkingService service;

    @GetMapping
    public ResponseEntity<List<ParkingModel>> findAll() {
        List<ParkingModel> entity = service.findAll();
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping(value = "/findBy/{id}")
    public ResponseEntity<ParkingModel> findById(@PathVariable int id) {
        ParkingModel obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

}
