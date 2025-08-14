package soa.ApiGateway.service;

import soa.ApiGateway.Classes.ApiRegistrationClass;
import soa.ApiGateway.Classes.KeyCloakUserInfoResponse;
import soa.ApiGateway.Classes.TokenInfo;
import soa.ApiGateway.Classes.UserInfo;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IKeyCloakService {
    UserInfo getUserInfo(String accessToken) throws IOException, InterruptedException;
    TokenInfo refreshToken(String accessToken) throws IOException, InterruptedException;
    boolean isUserExists(String username) throws IOException, InterruptedException;
    void createUser(ApiRegistrationClass apiRegistrationClass) throws IOException, InterruptedException, IllegalArgumentException;
    TokenInfo authenticateUser(String username, String password) throws IOException, InterruptedException, IllegalArgumentException;
    KeyCloakUserInfoResponse validate(String accessToken) throws IOException, InterruptedException, URISyntaxException;
    void logout(String accessToken) throws IOException, InterruptedException, URISyntaxException;
}
