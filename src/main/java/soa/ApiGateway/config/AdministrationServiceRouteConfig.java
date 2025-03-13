package soa.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministrationServiceRouteConfig {

    @Bean
    public RouteLocator adminRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Admin routes - restricted to ADMIN role
                .route("admin-service-route", r -> r
                        .path("/api/hello/**")
                        .filters(f -> f
                                .rewritePath("/api/admin/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
                        )
                        .uri("http://localhost:8081")).build();
    }
}
