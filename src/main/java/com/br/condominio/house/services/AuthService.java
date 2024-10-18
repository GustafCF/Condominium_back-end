package com.br.condominio.house.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.models.dto.LoginRequest;
import com.br.condominio.house.models.dto.LoginResponse;
import com.br.condominio.house.repositories.FunctionaryRepository;
import com.br.condominio.house.repositories.ResidentRepository;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final ResidentRepository residentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FunctionaryRepository functionaryRepository;

    public AuthService(JwtEncoder jwtEncoder, ResidentRepository residentRepository, BCryptPasswordEncoder passwordEncoder, FunctionaryRepository functionaryRepository) {
        this.jwtEncoder = jwtEncoder;
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
        this.functionaryRepository = functionaryRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var user = residentRepository.findByUsername(loginRequest.name()).orElseThrow(() -> new BadCredentialsException("Usuário invalido"));

        if (!user.LoginValidation(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Senha inválida!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.getRoles()
                .stream()
                .map(RoleModel::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("back-end")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue, expiresIn);
    }

    public LoginResponse loginFunc(LoginRequest loginRequest) {
        var fun = functionaryRepository.findByUsername(loginRequest.name()).orElseThrow(() -> new BadCredentialsException("Usuário invalido"));

        if (!fun.LoginValidationFunc(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Senha inválida!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = fun.getLs_roles()
                .stream()
                .map(RoleModel::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("back-end")
                .subject(fun.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue, expiresIn);
    }

}
