package com.hospital.msvc_especialidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsvcEspecialidadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcEspecialidadesApplication.class, args);
	}

}
