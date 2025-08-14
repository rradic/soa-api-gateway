package soa.ApiGateway.Classes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfo {
    private String sub;
    private String email;
    private String email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    public UserInfo() {
    }

    public UserInfo(
            String sub,
            String email,
            String email_verified,
            String name,
            String preferred_username,
            String given_name,
            String family_name
    ) {
        this.sub = sub;
        this.email = email;
        this.email_verified = email_verified;
        this.name = name;
        this.preferred_username = preferred_username;
        this.given_name = given_name;
        this.family_name = family_name;
    }

}
