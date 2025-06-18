package soa.ApiGateway.filters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalAuthenticationFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (path.startsWith("/api/public")) {
            return chain.filter(exchange); // Skip filtering for /api/public
        }
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .flatMap(authentication -> {
                    ServerHttpRequest mutatedRequest = mutateRequestWithAuthInfo(exchange.getRequest(), authentication);
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                }).switchIfEmpty(chain.filter(exchange));
    }

    private ServerHttpRequest mutateRequestWithAuthInfo(ServerHttpRequest request, Authentication authentication) {
        ServerHttpRequest.Builder mutatedRequest = request.mutate();

        // Add common authentication headers
        mutatedRequest.header("X-Auth-UserId", getUserId(authentication));

        // Add user roles as a header
        String roles = String.join(",", authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .toList());
        mutatedRequest.header("X-Auth-Roles", roles);

        // Extract additional details based on token type
        if (authentication instanceof JwtAuthenticationToken) {
            handleJwtAuthentication(mutatedRequest, (JwtAuthenticationToken) authentication);
        } else if (authentication instanceof OAuth2AuthenticationToken) {
            handleOAuth2Authentication(mutatedRequest, (OAuth2AuthenticationToken) authentication);
        }

        return mutatedRequest.build();
    }

    private String getUserId(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken().getSubject();
        } else if (authentication instanceof OAuth2AuthenticationToken) {
            return ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("sub");
        }
        return "unknown";
    }

    private void handleJwtAuthentication(ServerHttpRequest.Builder requestBuilder, JwtAuthenticationToken jwtAuth) {
        // Add JWT-specific headers if needed
        String tokenValue = jwtAuth.getToken().getTokenValue();
        // Forward the token to the backend services
        requestBuilder.header("Authorization", "Bearer " + tokenValue);

        // You can extract additional claims and add them as headers if needed
        jwtAuth.getToken().getClaims().forEach((key, value) -> {
            if (value != null && isSimpleValue(value)) {
                requestBuilder.header("X-Auth-" + key, value.toString());
            }
        });
    }

    private void handleOAuth2Authentication(ServerHttpRequest.Builder requestBuilder, OAuth2AuthenticationToken oAuth2Auth) {
        // Add OAuth2-specific headers if needed
        oAuth2Auth.getPrincipal().getAttributes().forEach((key, value) -> {
            if (value != null && isSimpleValue(value)) {
                requestBuilder.header("X-Auth-" + key, value.toString());
            }
        });
    }

    private boolean isSimpleValue(Object value) {
        return value instanceof String || value instanceof Number || value instanceof Boolean;
    }
}