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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.CondModel;
import com.br.condominio.house.services.CondService;

@RestController
@RequestMapping(value="/Conds")
public class CondController {

    @Autowired
    private CondService service;

    @GetMapping
    public ResponseEntity<List<CondModel>> findAll(){
        List<CondModel> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value="{id}")
    public ResponseEntity<CondModel> findById(@PathVariable UUID id){
        CondModel obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CondModel> insert(@RequestBody CondModel entity){
        var obj = service.insert(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    public ResponseEntity<CondModel> update(@PathVariable UUID id, @RequestBody CondModel entity){
        var obj = service.update(id, entity);
        return ResponseEntity.ok().body(obj);

    }

}