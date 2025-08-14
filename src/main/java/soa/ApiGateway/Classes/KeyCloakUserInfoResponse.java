package soa.ApiGateway.Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyCloakUserInfoResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean emailVerified;
    private Map<String, List<String>> attributes;
    private UserProfileMetadata userProfileMetadata;
    private boolean enabled;
    private String self;
    private String origin;
    private long createdTimestamp;
    private boolean totp;
    private String federationLink;
    private String serviceAccountClientId;
    private List<Credential> credentials;
    private List<String> disableableCredentialTypes;
    private List<String> requiredActions;
    private List<FederatedIdentity> federatedIdentities;
    private List<String> realmRoles;
    private Map<String, List<String>> clientRoles;
    private List<ClientConsent> clientConsents;
    private long notBefore;
    private List<String> groups;
    private Map<String, Boolean> access;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfileMetadata {
        private List<Attribute> attributes;
        private List<Group> groups;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attribute {
        private String name;
        private String displayName;
        private boolean required;
        private boolean readOnly;
        private Map<String, String> annotations;
        private Map<String, Map<String, String>> validators;
        private String group;
        private boolean multivalued;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Group {
        private String name;
        private String displayHeader;
        private String displayDescription;
        private Map<String, String> annotations;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credential {
        private String id;
        private String type;
        private String userLabel;
        private long createdDate;
        private String secretData;
        private String credentialData;
        private int priority;
        private String value;
        private boolean temporary;
        private String federationLink;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FederatedIdentity {
        private String identityProvider;
        private String userId;
        private String userName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientConsent {
        private String clientId;
        private List<String> grantedClientScopes;
        private long createdDate;
        private long lastUpdatedDate;
    }
}
