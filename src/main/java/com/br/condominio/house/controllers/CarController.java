package com.br.condominio.house.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.services.CarService;


@RestController
@RequestMapping(value = "/cars")
public class CarController {

    @Autowired
    private CarService service;

    @GetMapping
    public ResponseEntity<List<CarModel>> findAll(){
        List<CarModel> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    // @PostMapping("/register")
    // public ResponseEntity<CarModel> insert(@RequestBody CarModel car){
    //     CarModel obj = service.insert(car);
    //     URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
    //     return ResponseEntity.created(uri).body(obj);
    // }

    // @PostMapping
    // public ResponseEntity<CarModel> insert(@RequestBody CarModel car){
    //     CarModel obj = service.insert(car);
    //     URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
    //     return ResponseEntity.created(uri).body(obj);
    // }

    @PostMapping("/register/{residentId}")
    public ResponseEntity<CarModel> registerCar(@PathVariable UUID residentId, @RequestBody CarModel carModel) {
            CarModel savedCar = service.registerCarForResident(residentId, carModel);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri();
            return ResponseEntity.created(uri).body(savedCar);
    }        
}
