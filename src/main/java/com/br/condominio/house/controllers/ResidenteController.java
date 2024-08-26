package com.br.condominio.house.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.services.ResidentService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/res")
public class ResidenteController {

    @Autowired
    private ResidentService service;

    @GetMapping("/find")
    public ResponseEntity<List<ResidentModel>> findByName(@RequestParam String name) {
        List<ResidentModel> residents = service.findByName(name);
        return ResponseEntity.ok().body(residents);
    }
    
    @GetMapping
    public ResponseEntity<List<ResidentModel>> findAll(){
        List<ResidentModel> obj= service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResidentModel> findById(@PathVariable UUID id){
        var obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<ResidentModel> insert(@RequestBody ResidentModel entity){
        ResidentModel obj = service.insert(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ResidentModel> update(@PathVariable UUID id, @RequestBody ResidentModel entity) {
        var obj = service.update(id, entity);
        return ResponseEntity.ok().body(obj);
    }


}
