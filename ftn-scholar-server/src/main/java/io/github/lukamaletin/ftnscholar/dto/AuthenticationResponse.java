package io.github.lukamaletin.ftnscholar.dto;

import io.github.lukamaletin.ftnscholar.model.user.BaseUser;

public class AuthenticationResponse {

    private BaseUser user;

    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(BaseUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public BaseUser getUser() {
        return user;
    }

    public void setUser(BaseUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
