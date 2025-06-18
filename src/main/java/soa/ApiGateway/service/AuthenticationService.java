package soa.ApiGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soa.ApiGateway.Classes.TokenInfo;

import java.io.IOException;

@Service
public class AuthenticationService  implements IAuthService {

    @Autowired
    private IKeyCloakService keycloakService;

    @Override
    public TokenInfo login(String username, String password) throws IOException, InterruptedException, IllegalArgumentException {
        return keycloakService.authenticateUser(username, password);
    }

    @Override
    public void register(String username, String email, String password, String firstName, String lastName) {

    }

    @Override
    public void googleLogin(String googleToken) {

    }

    @Override
    public void logout() {

    }
}
