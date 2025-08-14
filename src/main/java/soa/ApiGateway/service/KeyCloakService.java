package soa.ApiGateway.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import soa.ApiGateway.Classes.ApiRegistrationClass;
import soa.ApiGateway.Classes.KeyCloakUserInfoResponse;
import soa.ApiGateway.Classes.TokenInfo;
import soa.ApiGateway.Classes.UserInfo;
import java.io.IOException;
import java.net.URI;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create( System.getenv("KEYCLOAK_URL") + "/admin/realms/" + System.getenv("KEYCLOAK_REALM") + "/users"))
                .POST(HttpRequest.BodyPublishers.ofString(gsonBuilder.create().toJson(apiRegistrationClass)))
                .header("Content-Type", "application/json")
                .header("Authorization", "bearer " + getAdminToken().getAccess_token())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new IllegalArgumentException("User creation failed with status code: " + response.statusCode());
        }
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
    }


    @Override
    public UserInfo getUserInfo(String accessToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(SERVER_URI_STRING + "/userinfo"))
                .POST(HttpRequest.BodyPublishers.ofString("access_token=" + accessToken))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("FORBIDEN");
        }

        Gson gson = new GsonBuilder().create();
       return gson.fromJson(response.body(), UserInfo.class);
    }

    @Override
    public TokenInfo refreshToken(String refreshToken) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("client_secret", System.getenv("KEYCLOAK_CLIENT_SECRET"));
        params.put("client_id", System.getenv("KEYCLOAK_CLIENT_ID"));
        params.put("refresh_token", refreshToken);
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
    }

    @Override
    public boolean isUserExists(String username) throws IOException, InterruptedException {
        return getUserByUsername(username) != null;
    }

    public KeyCloakUserInfoResponse getUserByUsername(String username) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
//        params.put("grant_type", "secrete");
//        params.put("client_secret", System.getenv("KEYCLOAK_CLIENT_SECRET"));
//        params.put("client_id", System.getenv("KEYCLOAK_CLIENT_ID"));
        params.put("username", username);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create( System.getenv("KEYCLOAK_URL") + "/admin/realms/" + System.getenv("KEYCLOAK_REALM") + "/users"))
                .POST(getParamsUrlEncoded(params))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new GsonBuilder().create();
        KeyCloakUserInfoResponse[] array = gson.fromJson(response.body(), KeyCloakUserInfoResponse[].class);
        if (array.length == 0) {
            return null;
        }

        return array[0];
    }

    private HttpRequest.BodyPublisher getParamsUrlEncoded(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }

    public KeyCloakUserInfoResponse validate(String accessToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(SERVER_URI_STRING + "/introspect"))
                .POST(HttpRequest.BodyPublishers.ofString("access_token=" + accessToken))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("FORBIDEN");
        }

        Gson gson = new GsonBuilder().create();
        KeyCloakUserInfoResponse responseObj =  gson.fromJson(response.body(), KeyCloakUserInfoResponse.class);
        return responseObj;
    }

    @Override
    public void logout(String accessToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(SERVER_URI_STRING + "/logout"))
                .POST(HttpRequest.BodyPublishers.ofString("access_token=" + accessToken))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private TokenInfo getAdminToken() throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_secret", System.getenv("KEYCLOAK_CLIENT_SECRET"));
        params.put("client_id", System.getenv("KEYCLOAK_CLIENT_ID"));
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
    }
}
