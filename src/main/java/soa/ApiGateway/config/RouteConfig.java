package soa.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                // Admin routes - restricted to ADMIN role
//                .route("admin-service-route", r -> r
//                        .path("/api/admin/**")
//                        .filters(f -> f
//                                .rewritePath("/api/admin/(?<segment>.*)", "/${segment}")
//                                .addRequestHeader("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
//                        )
//                        .uri("localhost:8082"))

                // User management routes - accessible by ADMIN or USER_MANAGER roles
                .route("user-service-route", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .rewritePath("/api/users/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
                        )
                        .uri("http://localhost:8082"))

                // Product routes - accessible by ADMIN or PRODUCT_MANAGER roles
                .route("product-service-route", r -> r
                        .path("/api/products/**")
                        .filters(f -> f
                                .rewritePath("/api/products/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
                        )
                        .uri("http://localhost:8083"))

                // Order routes - accessible by ADMIN or SALES roles
                .route("order-service-route", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f
                                .rewritePath("/api/orders/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
                        )
                        .uri("http://localhost:8084"))

                // Public routes - accessible by all authenticated users
                .route("public-service-route", r -> r
                        .path("/api/public/**")
                        .filters(f -> f
                                .rewritePath("/api/public/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Gateway-Timestamp", String.valueOf(System.currentTimeMillis()))
                        )
                        .uri("http://localhost:8085"))

                .build();
    }
}
