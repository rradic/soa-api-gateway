package soa.ApiGateway.service;

import soa.ApiGateway.Classes.TokenInfo;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IKeyCloakService {
    String getUserInfo(String accessToken);
    TokenInfo refreshToken(String accessToken, String refreshToken);
    boolean isUserExists(String username);
    void createUser(String username, String email, String password);
    TokenInfo authenticateUser(String username, String password) throws IOException, InterruptedException, IllegalArgumentException;
}
