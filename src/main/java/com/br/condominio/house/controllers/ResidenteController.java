package com.br.condominio.house.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.services.ResidentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/res")
public class ResidenteController {

    @Autowired
    private ResidentService service;

    @Operation(
            summary = "Buscar Residente",
            description = "Endpoint para buscar residente no banco de dados pelo Nome"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = ResidentModel.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização para realizar a requisição",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/find/{name}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Optional<ResidentModel>> findByName(@PathVariable String name) {
        Optional<ResidentModel> residents = service.findByName(name);
        return ResponseEntity.ok().body(residents);
    }

    @Operation(
            summary = "Listar Residentes",
            description = "Endpoint para listar os residentes do condomínio"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = ResidentModel.class))),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização para realizar a requisição",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/total")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<ResidentModel>> findAll() {
        List<ResidentModel> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @Operation(
            summary = "Buscar Residente",
            description = "Endpoint para buscar residente no banco de dados pelo Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = ResidentModel.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização para realizar a requisição",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<ResidentModel> findById(@PathVariable Long id) {
        var obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(
            summary = "Cadastro de residentes",
            description = "Cadastra residentes de um residente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),})
    @PostMapping(value = "/insert/{ap}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<ResidentModel> insert(@PathVariable int ap, @RequestBody ResidentModel entity) {
        ResidentModel obj = service.insert(ap, entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(
            summary = "Adicionar apartamento",
            description = "Endpoint para adicioanr um apartammento a um usuário"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added Successfullly!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),})
    @PostMapping(value = "/at/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")

    public ResponseEntity<ResidentModel> addAp(@PathVariable Long id, @RequestBody int ap) {
        ResidentModel obj = service.addAp(id, ap);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Exlcuir residente da base de dados",
            description = "Remove um residente do sistema. Requer autorização de adminitrador",
            tags = {"Funcionários"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Residente excluído com sucesso",
                content = @Content()),
        @ApiResponse(responseCode = "404", description = "Residente não encontrado",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Você não tem permisão para acessar esta rota",
                content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualizar residente",
            description = "Atualizar residente na base de dados com seu Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ResidentModel> update(@PathVariable Long id, @RequestBody ResidentModel entity, JwtAuthenticationToken token) {
        var obj = service.update(id, entity, token);
        return ResponseEntity.ok().body(obj);
    }

}
