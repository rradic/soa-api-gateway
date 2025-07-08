package soa.ApiGateway.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import soa.ApiGateway.Classes.ApiRegistrationClass;
import soa.ApiGateway.Classes.TokenInfo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KeyCloakService implements IKeyCloakService {

    private static final String SERVER_URI_STRING =
            System.getenv("KEYCLOAK_URL") +
            "/realms/" +
            System.getenv("KEYCLOAK_REALM") +
            "/protocol/openid-connect";

    private final HttpClient client = HttpClient.newBuilder().build();

    // Implement methods for KeyCloak integration here
    // For example, methods to create users, authenticate, etc.

    @Override
    public void createUser(ApiRegistrationClass apiRegistrationClass) throws IOException, InterruptedException, IllegalArgumentException {

        // Implementation for creating a user in KeyCloak
    }

    @Override
    public TokenInfo authenticateUser(String username, String password) throws IOException, InterruptedException, IllegalArgumentException {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_secret", System.getenv("KEYCLOAK_CLIENT_SECRET"));
        params.put("client_id", System.getenv("KEYCLOAK_CLIENT_ID"));
        params.put("username", username);
        params.put("password", password);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(SERVER_URI_STRING + "/token"))
                .POST(getParamsUrlEncoded(params))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalArgumentException("Authentication failed with status code: " + response.statusCode());
        }
        System.out.println(response);

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response.body(), TokenInfo.class);
        // Implementation for authenticating a user in KeyCloak
    }

    @Override
    public String getUserInfo(String accessToken) {
        return "";
    }

    @Override
    public TokenInfo refreshToken(String refreshToken, String accessToken) {
        // Implementation for refreshing a token in KeyCloak
        return null;
    }

    @Override
    public boolean isUserExists(String username) {
        return false;
    }

    private HttpRequest.BodyPublisher getParamsUrlEncoded(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }

//    public KeyCloakUserInfoResponse validate(String accessToken) throws IOException, InterruptedException {
//        SERVER_URI = URI.create(BASE_URL+REALM+VALIDATE);
//        log.info("SERVER_URI: "+SERVER_URI);
//        HttpRequest request = HttpRequest
//                .newBuilder()
//                .uri(SERVER_URI)
//                .POST(HttpRequest.BodyPublishers.ofString("access_token=" + accessToken))
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        log.info(String.valueOf(response.statusCode()));
//        if (response.statusCode() != 200) {
//            throw new RuntimeException("FORBIDEN");
//        }
//
//        Gson gson = new GsonBuilder().create();
//        KeyCloakUserInfoResponse responseObj =  gson.fromJson(response.body(), KeyCloakUserInfoResponse.class);
//        log.info(responseObj.toString());
//        return responseObj;
//    }
}
