package com.br.condominio.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.br.condominio.house.config.FileStorageConfig;

@EnableConfigurationProperties({FileStorageConfig.class})
@SpringBootApplication
public class HouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}

}
