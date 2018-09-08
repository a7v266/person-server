package org.sandbox.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {

    @JsonProperty
    private String username;

    @JsonProperty
    private String token;

    public void setCurrentUser(User user) {
        username = user.getUsername();
        token = user.getToken();
    }
}
