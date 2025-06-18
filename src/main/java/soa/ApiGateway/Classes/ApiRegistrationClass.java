package soa.ApiGateway.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRegistrationClass {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private boolean emailVerified;
    private boolean enabled;
    private Credentials[] credentials;

    @Getter
    @Setter
    private class Credentials {
        private String type;
        private String value;

        public Credentials(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public Credentials() {}
    }

    public ApiRegistrationClass() {}

    public ApiRegistrationClass(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailVerified = false; // Default value
        this.enabled = true; // Default value
        this.credentials = new Credentials[]{new Credentials("password", password)};
    }
}
