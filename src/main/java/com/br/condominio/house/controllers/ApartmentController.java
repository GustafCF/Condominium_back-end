package com.br.condominio.house.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.ApartmentModel;
import com.br.condominio.house.services.ApartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/apt")
public class ApartmentController {

    @Autowired
    private ApartmentService service;

    @Operation(
            summary = "Lista de Apartamentos",
            description = "Busca no banco todos os apartamentos do condomínio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = ApartmentModel.class))),
        @ApiResponse(responseCode = "403", description = "Error! Resource unauthorized",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Error! Need authorization!",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/ls")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ApartmentModel>> findAll() {
        List<ApartmentModel> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Buscar apartamento",
            description = "Buscar um aoartamento específico com base no seub Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = ApartmentModel.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Error! Resource unauthorized",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Error! Need authorization!",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/find/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<ApartmentModel> finById(@PathVariable int id) {
        ApartmentModel ap = service.finById(id);
        return ResponseEntity.ok().body(ap);
    }

}
