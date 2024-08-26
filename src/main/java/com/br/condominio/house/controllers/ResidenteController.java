package com.br.condominio.house.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.services.ResidentService;

@RestController
@RequestMapping(value = "/res")
public class ResidenteController {

    @Autowired
    private ResidentService service;

    @GetMapping("/residents")
    public ResponseEntity<List<ResidentModel>> getResidentsByName(@RequestParam String name) {
        List<ResidentModel> residents = service.findByName(name);
        return ResponseEntity.ok().body(residents);
    }

}
