
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.services.DependentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping(value = "/dependent")
public class DependentController {

    @Autowired
    private DependentService service;

    @Operation(
            summary = "Listar depedentes",
            description = "Lista dependentes de um residente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<DependentModel>> findAll() {
        List<DependentModel> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Buscar dependente por id",
            description = "Busca um dependente no banco de dados de com base no seu Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Resource not found!",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<DependentModel> findById(@PathVariable Long id) {
        DependentModel obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(
            summary = "Buscar dependente pelo nome",
            description = "Busca um dependente no banco de dados de com base no seu nome"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Resource not found!",
                content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/name/{name}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<DependentModel>> findByName(@PathVariable String name) {
        List<DependentModel> list = service.findByName(name);
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Cadastro de depedentes",
            description = "Cadastra dependentes de um residente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),})
    @PostMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<DependentModel> insert(@PathVariable Long id, @RequestBody DependentModel entity) {
        var obj = service.insert(id, entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(summary = "Exlcuir dependente da base de dados",
            description = "Remove um depedente do sistema. Requer autorização de adminitrador",
            tags = {"Funcionários"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso",
                content = @Content()),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
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
            summary = "Atualizar depedentes",
            description = "Atualizar dependentes de um residente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success!",
                content = @Content(schema = @Schema(implementation = DependentModel.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorized!",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                content = @Content(mediaType = "application/json"))        
    })
    @PutMapping(value = "/atl")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<DependentModel> update(@PathVariable Long id, @RequestBody DependentModel obj) {
        DependentModel entity = service.update(id, obj);
        return ResponseEntity.ok().body(entity);
    }

}
