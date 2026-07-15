package com.hospital.msvc_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 1. Pacientes (Redirige directo al puerto local 8081 para evitar fallos de resolución)
                .route("msvc-pacientes", r -> r.path("/api/v1/pacientes/**")
                        .uri("http://localhost:8081"))
                
                // 2. Médicos
                .route("msvc-medicos", r -> r.path("/api/v1/medicos/**")
                        .uri("http://localhost:8082"))
                
                // 3. Especialidades
                .route("msvc-especialidades", r -> r.path("/api/v1/especialidades/**")
                        .uri("http://localhost:8083"))
                
                // 4. Citas Médicas
                .route("msvc-citas", r -> r.path("/api/v1/citas/**")
                        .uri("http://localhost:8084"))
                
                // 5. Fichas Clínicas
                .route("msvc-fichas", r -> r.path("/api/v1/fichas/**")
                        .uri("http://localhost:8085"))
                
                // 6. Recetas
                .route("msvc-recetas", r -> r.path("/api/v1/recetas/**")
                        .uri("http://localhost:8086"))
                
                // 7. Exámenes
                .route("msvc-examenes", r -> r.path("/api/v1/examenes/**")
                        .uri("http://localhost:8087"))
                
                // 8. Hospitalización
                .route("msvc-hospitalizacion", r -> r.path("/api/v1/hospitalizacion/**")
                        .uri("http://localhost:8088"))
                
                // 9. Urgencias
                .route("msvc-urgencias", r -> r.path("/api/v1/urgencias/**")
                        .uri("http://localhost:8089"))
                
                // 10. Pagos
                .route("msvc-pagos", r -> r.path("/api/v1/pagos/**")
                        .uri("http://localhost:8090"))
                
                .build();
    }

}
