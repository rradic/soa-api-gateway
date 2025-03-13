package soa.ApiGateway;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
public class ApiGatewayApplication {

	public static Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
	public static void main(String[] args) {
		logger.info("Starting API Gateway");
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
//
//@Configuration
//class SecurityConfiguration {
//	private final String audience;
//	private final ReactiveClientRegistrationRepository clientRegistrationRepository;
//
//	SecurityConfiguration(
//			ReactiveClientRegistrationRepository clientRegistrationRepository,
//			@Value("${auth0.audience}") String audience
//
//	) {
//		this.audience = audience;
//		this.clientRegistrationRepository = clientRegistrationRepository;
//	}
//
////	@Bean
////	public ReactiveClientRegistrationRepository clientRegistrationRepository() {
////		ClientRegistration registration = ClientRegistration
////				.withRegistrationId("auth0")
////				.clientId("${AUTH0_CLIENT_ID}")
////				.clientSecret("${AUTH0_CLIENT_SECRET}")
////				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
////				.scope("openid")
////				.authorizationUri("https://dev-0pv2np4v5ka0qkpk.us.auth0.com/authorize")
////				.tokenUri("https://dev-0pv2np4v5ka0qkpk.us.auth0.com/oauth/token")
////				.userInfoUri("https://dev-0pv2np4v5ka0qkpk.us.auth0.com/userinfo")
////				.jwkSetUri("https://dev-0pv2np4v5ka0qkpk.us.auth0.com/.well-known/jwks.json")
////				.clientName("Auth0")
////				.build();
////
////		return new InMemoryReactiveClientRegistrationRepository(registration);
////	}
//
//	@Bean
//	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//		http
//			.authorizeExchange(authorizeExchangeSpec ->
//					authorizeExchangeSpec.anyExchange().authenticated()
//			)
////				.pathMatchers("/hello").authenticated()
////				.anyExchange().permitAll()
////				.and()
//				.oauth2Login(oauth2Login -> oauth2Login.authorizationRequestResolver(
//						authorizationRequestResolver(this.clientRegistrationRepository)
//						)
//				);
////			.oauth2ResourceServer()
////				.jwt()
////				.jwkSetUri("https://dev-7v6z1v7v.us.auth0.com/.well-known/jwks.json")
////				.jwtAuthenticationConverter(new JwtAuthenticationConverter(audience));
//		return http.build();
//	}
//
//	private ServerOAuth2AuthorizationRequestResolver authorizationRequestResolver(ReactiveClientRegistrationRepository clientRegistrationRepository) {
//		var authorizationRequestResolver = new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository);
//		authorizationRequestResolver.setAuthorizationRequestCustomizer(authorizationRequestCustomizer());
//		return null;
//	}
//
//	private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
//		return builder -> builder.additionalParameters(params -> params.put("audience", audience));
//	}
//
//}
//
//@RestController
//@ResponseBody
//class Controller {
//
//	@GetMapping("/hello")
//	public Map<String, String> helloWorld(Principal principal) {
//		return Map.of("message", "hello: " + principal.toString());
//	}
//	@GetMapping("/hello2")
//	public String helloWorld2() {
//
//		return "Hello, World2222!";
//	}
//}
