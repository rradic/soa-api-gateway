package soa.ApiGateway.service;

import soa.ApiGateway.Classes.TokenInfo;

import java.io.IOException;

public interface IAuthService {

    TokenInfo login(String username, String password) throws IOException, InterruptedException;

    void register(String username, String email, String password, String firstName, String lastName);

    void googleLogin(String googleToken);

    void logout();
}
