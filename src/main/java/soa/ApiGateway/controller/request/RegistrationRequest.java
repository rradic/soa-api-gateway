package soa.ApiGateway.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // No-args constructor
    public RegistrationRequest() {}

    // All-args constructor
    public RegistrationRequest(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
