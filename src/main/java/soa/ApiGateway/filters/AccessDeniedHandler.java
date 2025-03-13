package soa.ApiGateway.filters;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AccessDeniedHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.warn("Access denied for path: {}", exchange.getRequest().getPath());

        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.FORBIDDEN.value());
        responseBody.put("error", "Forbidden");
        responseBody.put("message", "Access denied: Insufficient permissions");
        responseBody.put("path", exchange.getRequest().getPath().value());

        byte[] responseBytes;
        try {
            responseBytes = objectMapper.writeValueAsBytes(responseBody);
        } catch (JsonProcessingException e) {
            log.error("Error writing JSON response", e);
            return Mono.error(e);
        }

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(responseBytes)));
    }
}