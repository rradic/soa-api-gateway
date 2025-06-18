package soa.ApiGateway.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleTokenRequest {
    private String idToken;

    public GoogleTokenRequest() {
    }

    public GoogleTokenRequest(String idToken) {
        this.idToken = idToken;
    }
}
