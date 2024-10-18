package com.br.condominio.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.br.condominio.house.config.FileStorageConfig;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@EnableConfigurationProperties({FileStorageConfig.class})
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "CONDOMINIUM MANAGER(Software de Gerenciamento de Condomínios)",
                version = "1.0",
                description = "A API foi desenvolvida com Java e Spring Boot para gerenciar e suprir todas as necessecidades que um condomínio tem, utilizando de criptografia e protocólos de segurança avançados disponibilizados pelo framework ",
                contact = @Contact(
                        name = "Suporte API",
                        email = "suport@api.com",
                        url = "https://api.com/suporte"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
            @Server(url = "https://api.exemplo.com", description = "Servidor de Produção"),
            @Server(url = "https://api-staging.exemplo.com", description = "Servidor de Staging")
        },
        externalDocs = @ExternalDocumentation(
                description = "Documentação Completa da API",
                url = "https://api.exemplo.com/docs"
        ),
        tags = {
            @Tag(name = "Funcionários", description = "Endpoints relacionados a funcionários"),
            @Tag(name = "Login", description = "Endpoints para autenticação"),
            @Tag(name = "Arquivos", description = "Endpoints para arquivos")
        }
)
public class HouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseApplication.class, args);
    }

}
