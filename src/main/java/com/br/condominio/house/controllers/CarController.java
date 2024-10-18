package com.br.condominio.house.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.services.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    @Autowired
    private CarService service;

    @Operation(
            summary = "Listar veículos",
            description = "Endpoint para listagem de veículos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = CarModel.class))),
        @ApiResponse(responseCode = "403", description = "Erro! User Unauthorized.",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Erro! Need authorization",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/list")
    @PreAuthorize("hasAutority('SCOPE_admin')")
    public ResponseEntity<List<CarModel>> findAll() {
        List<CarModel> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @Operation(
            summary = "Buscar veículo",
            description = "Buscar um veículo específico no banco de dados com base no seu Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = CarModel.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Erro! User Unauthorized.",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Erro! Need authorization",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/list/{id}")
    @PreAuthorize("hasAutority('SCOPE_admin')")
    public ResponseEntity<CarModel> findById(@PathVariable Long id) {
        CarModel obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(
            summary = "Registro de veículos",
            description = "Endpoint de registro de veículos no banco de dados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created Success!",
                content = @Content(schema = @Schema(implementation = CarModel.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found!",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Erro! Unauthorized",
                content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register/{residentId}")
    @PreAuthorize("hasAutority('SCOPE_admin')")
    public ResponseEntity<CarModel> registerCar(@PathVariable Long residentId, @RequestBody CarModel carModel) {
        CarModel savedCar = service.registerCarForResident(residentId, carModel);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri();
        return ResponseEntity.created(uri).body(savedCar);
    }

    @Operation(
            summary = "Remover veículo",
            description = "Endpoint para remoção de um veículo do banco de dados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content!",
                content = @Content()),
        @ApiResponse(responseCode = "404", description = "Resource not found!",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Unauthotized!",
                content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
