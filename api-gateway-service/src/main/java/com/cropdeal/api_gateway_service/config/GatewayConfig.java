package com.cropdeal.api_gateway_service.config;

//import com.cropdeal.api_gateway_service.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
        public RouteLocator routes(RouteLocatorBuilder builder) {
            return builder.routes()
                    .route("farmer-service", r -> r.path("/api/farmers/**")
                            .uri("http://localhost:8080"))
                    .route("dealer-service", r -> r.path("/api/dealers/**")
                            .uri("http://localhost:8082"))
                    .route("admin-service", r -> r.path("/Admin/**")
                            .uri("http://localhost:7499"))
                    .build();
        }

    }

