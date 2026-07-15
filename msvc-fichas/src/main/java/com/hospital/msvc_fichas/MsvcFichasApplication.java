package com.hospital.msvc_fichas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsvcFichasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcFichasApplication.class, args);
	}

}
