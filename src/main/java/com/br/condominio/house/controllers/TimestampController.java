package com.br.condominio.house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.TimestampModel;
import com.br.condominio.house.services.TimestampService;

@RestController
@RequestMapping(value = "/ticket")
public class TimestampController {

    @Autowired
    private TimestampService service;

    @PostMapping(value = "/access/{id}")
    public ResponseEntity<TimestampModel> ticket(@PathVariable Long id) {
        TimestampModel time = service.times(id);
        return ResponseEntity.ok().body(time);
    }

}
