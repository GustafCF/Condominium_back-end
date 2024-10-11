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
        // Tente encontrar o usuário como residente
        var user = residentRepository.findByUsername(loginRequest.name());
        // Tente encontrar o funcionário
        var func = functionaryRepository.findByUsername(loginRequest.name());

        // Validação do residente
        if (user.isPresent() && user.get().LoginValidation(loginRequest, passwordEncoder)) {
            // Se o usuário for encontrado e a validação for bem-sucedida
            return createJwtForUser(user.get());
        }

        // Validação do funcionário
        if (func.isPresent() && func.get().LoginValidationFunc(loginRequest, passwordEncoder)) {
            // Se o funcionário for encontrado e a validação for bem-sucedida
            return createJwtForFunc(func.get());
        }

        // Se nenhum usuário ou funcionário for encontrado ou a validação falhar
        throw new BadCredentialsException("Usuário ou senha inválida");
    }

    private LoginResponse createJwtForUser(ResidentModel user) {
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

    private LoginResponse createJwtForFunc(FunctionaryModel func) {
        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = func.getLs_roles()
                .stream()
                .map(RoleModel::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("back-end")
                .subject(func.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue, expiresIn);
    }

}
