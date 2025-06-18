package soa.ApiGateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private final Logger log = LoggerFactory.getLogger(AccessDeniedHandler.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Generate a request ID
        String requestId = UUID.randomUUID().toString();

        // Log the incoming request
        log.info("Request: [{}] {} {} from {}",
                requestId,
                request.getMethod(),
                request.getURI(),
                request.getRemoteAddress());

        // Add the request ID as a header and pass it to downstream services
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-Request-ID", requestId)
                .build();

        // Log the response status when completed
        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                .then(Mono.fromRunnable(() ->
                        log.info("Response: [{}] with status {}",
                                requestId,
                                exchange.getResponse().getStatusCode())
                ));
    }

    @Override
    public int getOrder() {
        // Run this filter before authentication and other filters
        return -1;
    }
}