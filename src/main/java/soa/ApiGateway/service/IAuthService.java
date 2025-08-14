package soa.ApiGateway.service;

import soa.ApiGateway.Classes.TokenInfo;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IAuthService {

    TokenInfo login(String username, String password) throws IOException, InterruptedException;

    TokenInfo register(String username, String email, String password, String firstName, String lastName) throws IOException, InterruptedException;

    void googleLogin(String googleToken);

    void logout(String token) throws IOException, InterruptedException, URISyntaxException;
}
