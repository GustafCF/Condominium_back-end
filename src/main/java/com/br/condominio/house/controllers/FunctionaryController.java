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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.services.FunctionaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/fun")
public class FunctionaryController {

    private final FunctionaryService service;

    public FunctionaryController(FunctionaryService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastro de novo usuário",
            description = "Verifica as credenciais de um usuário para checar se ele tem autorização para cadastrar usuários, com base no seu papel pré-definido e o registra no banco de dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
                content = @Content(schema = @Schema(implementation = FunctionaryModel.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "O usuário não tem autorização para realizar a requisição",
                content = @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "/regis")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<FunctionaryModel> registrar(@RequestBody @Valid FunctionaryModel entity) {
        var functionary = service.registrar(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(functionary.getId()).toUri();
        return ResponseEntity.created(uri).body(functionary);
    }

    @Operation(summary = "Atualizar um funcionário", description = "Atualiza os dados de um funcionário existente. Requer autorização do funcionário correspondente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = FunctionaryModel.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Não autorizado: você não está autorizado a atualizar este funcionário",
                content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/atl/{id}")
    public ResponseEntity<FunctionaryModel> update(@PathVariable Long id, @RequestBody FunctionaryModel entity, @RequestHeader(value = "Authorization") @Parameter(description = "Token Bearer para a autenticação. Exemplo: 'Bearer token'", required = true) JwtAuthenticationToken token) {
        service.update(id, entity, token);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Exlcuir funcionário da base de dados", description = "Remove um funcionário do sistema. Requer autorização de adminitrador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso",
                content = @Content()),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Você não tem permisão para acessar esta rota",
                content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Void> delete(@PathVariable Long id,
            @RequestHeader(value = "Authorization")
            @Parameter(description = "Token Bearer para a autenticação. Exemplo: 'Bearer token'", required = true) JwtAuthenticationToken token) {
        service.delete(id, token);
        return ResponseEntity.noContent().build();
    }

}
