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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.services.DependentService;

@RestController
@RequestMapping(value = "/dependent")
public class DependentController {

    @Autowired
    private DependentService service;

    @GetMapping
    public ResponseEntity<List<DependentModel>> findAll(){
        List<DependentModel> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DependentModel> findById(@PathVariable UUID id){
        DependentModel obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<List<DependentModel>> findByName(@PathVariable String name){
        List<DependentModel> list = service.findByName(name);
        return ResponseEntity.ok().body(list);
    }


    @PostMapping(value = "/{id}")
    public ResponseEntity<DependentModel> insert(@PathVariable UUID id, @RequestBody DependentModel entity){
        var obj = service.insert(id, entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<DependentModel> update(@PathVariable UUID id, @RequestBody DependentModel obj){
        DependentModel entity = service.update(id, obj);
        return ResponseEntity.ok().body(entity);
    }


}
