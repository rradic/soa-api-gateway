package soa.ApiGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soa.ApiGateway.Classes.ApiRegistrationClass;
import soa.ApiGateway.Classes.TokenInfo;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class AuthenticationService  implements IAuthService {

    @Autowired
    private IKeyCloakService keycloakService;

    @Override
    public TokenInfo login(String email, String password) throws IOException, InterruptedException, IllegalArgumentException {
        return keycloakService.authenticateUser(email, password);
    }

    @Override
    public TokenInfo register(String username, String email, String password, String firstName, String lastName) throws IOException, InterruptedException {
        keycloakService.createUser(new ApiRegistrationClass(username, email, password, firstName, lastName));
        return keycloakService.authenticateUser(email, password);
    }

    @Override
    public void googleLogin(String googleToken) {

    }

    @Override
    public void logout(String token) throws IOException, URISyntaxException, InterruptedException {
        keycloakService.logout(token);
    }
}
