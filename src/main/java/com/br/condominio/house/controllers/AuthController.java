package com.br.condominio.house.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.condominio.house.models.dto.LoginRequest;
import com.br.condominio.house.models.dto.LoginResponse;
import com.br.condominio.house.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthService service;

    @Operation(
            summary = "Login",
            description = "Endpoint de login comum",
            tags = {"Login"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login success",
                content = @Content(schema = @Schema(implementation = LoginRequest.class))),
        @ApiResponse(responseCode = "403", description = "Usuário ou senha inválidados",
                content = @Content(mediaType = "application/json")),})
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = service.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(
            summary = "Login",
            description = "Endpoint de login comum",
            tags = {"Login"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login success",
                content = @Content(schema = @Schema(implementation = LoginRequest.class))),
        @ApiResponse(responseCode = "403", description = "Usuário ou senha inválidados",
                content = @Content(mediaType = "application/json")),})
    @PostMapping(value = "/login/func")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<LoginResponse> loginfunc(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = service.loginFunc(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }
}
