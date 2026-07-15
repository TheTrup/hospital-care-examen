package com.hospital.msvc_urgencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsvcUrgenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUrgenciasApplication.class, args);
	}

}
