package com.br.condominio.house.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.services.FunctionaryService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/fun")
public class FunctionaryController {

    private final FunctionaryService service;

    public FunctionaryController(FunctionaryService service) {
        this.service = service;
    }

    @PostMapping(value = "/regis")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<FunctionaryModel> registrar(@RequestBody @Valid FunctionaryModel entity) {
        var functionary = service.registrar(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(functionary.getId()).toUri();
        return ResponseEntity.created(uri).body(functionary);
    }

    @PutMapping(value = "/atl")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<FunctionaryModel> update(@RequestBody @Valid FunctionaryModel entity, @PathVariable Long id, JwtAuthenticationToken token) {
        service.update(id, entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {
        service.delete(id, token);
        return ResponseEntity.noContent().build();
    }

}
