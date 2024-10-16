package com.br.condominio.house.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.ParkingModel;
import com.br.condominio.house.services.ParkingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/park")
public class ParkingController {

    @Autowired
    private ParkingService service;

    @Operation(
            summary = "Listar Carros",
            description = "Endpoint para listagem de todas as vagas registrados no banco de dados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = ParkingModel.class))),
        @ApiResponse(responseCode = "403", description = "Unathorized!",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ParkingModel>> findAll() {
        List<ParkingModel> entity = service.findAll();
        return ResponseEntity.ok().body(entity);
    }

    @Operation(
            summary = "Buscar uma vaga",
            description = "Buscar uma vaga específica pelo seu Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(schema = @Schema(implementation = ParkingModel.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/findBy/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<ParkingModel> findById(@PathVariable int id) {
        ParkingModel obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

}
