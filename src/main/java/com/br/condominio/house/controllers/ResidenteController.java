package com.br.condominio.house.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.services.ResidentService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/res")
public class ResidenteController {

    @Autowired
    private ResidentService service;

    @GetMapping("/find/{name}")
    public ResponseEntity<List<ResidentModel>> findByName(@PathVariable String name) {
        List<ResidentModel> residents = service.findByName(name);
        return ResponseEntity.ok().body(residents);
    }
    
    @GetMapping
    public ResponseEntity<List<ResidentModel>> findAll(){
        List<ResidentModel> obj= service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResidentModel> findById(@PathVariable Long id){
        var obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/insert/{ap}")
    public ResponseEntity<ResidentModel> insert(@PathVariable int ap, @RequestBody ResidentModel entity){
        ResidentModel obj = service.insert(ap, entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PostMapping(value = "/at/{id}")
    public ResponseEntity<ResidentModel> addAp(@PathVariable Long id, @RequestBody int ap){
        ResidentModel obj = service.addAp(id, ap);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ResidentModel> update(@PathVariable Long id, @RequestBody ResidentModel entity) {
        var obj = service.update(id, entity);
        return ResponseEntity.ok().body(obj);
    }

}
