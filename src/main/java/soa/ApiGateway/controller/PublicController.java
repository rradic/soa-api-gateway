package soa.ApiGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soa.ApiGateway.Classes.TokenInfo;
import soa.ApiGateway.controller.request.LoginRequest;
import soa.ApiGateway.controller.request.RegistrationRequest;
import soa.ApiGateway.service.IAuthService;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) throws IOException, InterruptedException {
        try {
            TokenInfo tokenInfo = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(tokenInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequest request) {
        try {
            TokenInfo tokenInfo = authService.register(request.getUsername(), request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());
            return ResponseEntity.ok().body(tokenInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Invalid username or password");
        }
    }

//    @PostMapping("/google-login")
//    public void googleLogin(GoogleTokenRequest request) {
//        // Logic for Google login
//
//    }

    @GetMapping
    public void logout(@RequestHeader("Authorization") String token) throws IOException, URISyntaxException, InterruptedException {
        authService.logout(token);
    }
}
