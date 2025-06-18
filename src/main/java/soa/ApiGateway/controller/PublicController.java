package soa.ApiGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soa.ApiGateway.Classes.TokenInfo;
import soa.ApiGateway.controller.request.GoogleTokenRequest;
import soa.ApiGateway.controller.request.LoginRequest;
import soa.ApiGateway.controller.request.RegistrationRequest;
import soa.ApiGateway.service.IAuthService;

import java.io.IOException;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) throws IOException, InterruptedException {
        try {
            TokenInfo tokenInfo = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok().body(tokenInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest request) {
        // Logic for public registration

    }

    @PostMapping("/google-login")
    public void googleLogin(GoogleTokenRequest request) {
        // Logic for Google login

    }

    @GetMapping
    public String logout() {
        return "Hello World";
    }
}
