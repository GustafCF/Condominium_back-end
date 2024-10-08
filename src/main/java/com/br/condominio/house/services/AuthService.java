package com.br.condominio.house.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.br.condominio.house.models.RoleModel;
import com.br.condominio.house.models.dto.LoginRequest;
import com.br.condominio.house.models.dto.LoginResponse;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.RoleRepository;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final ResidentRepository residentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(JwtEncoder jwtEncoder, ResidentRepository residentRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.jwtEncoder = jwtEncoder;
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var user = residentRepository.findByResidentName(loginRequest.name());

        if (user.isEmpty() || !user.get().LoginValidation(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Usuário ou senha inválida");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(RoleModel::getName)
                .collect(Collectors.joining(""));

        var claims = JwtClaimsSet.builder()
                .issuer("back-end")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue, expiresIn);
    }

}
