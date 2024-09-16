package com.br.condominio.house.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.services.ApartmentService;

@RestController
@RequestMapping(value = "/apt")
public class ApartmentController {

    @Autowired
    private ApartmentService service;

    @GetMapping
    public ResponseEntity<List<ApartmentModel>> findAll() {
        List<ApartmentModel> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<ApartmentModel> finById(@PathVariable int id) {
        ApartmentModel ap = service.finById(id);
        return ResponseEntity.ok().body(ap);
    }

}
