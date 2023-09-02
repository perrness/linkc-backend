package com.linkc.linkcbackend.domain;

public class AuthenticationResponse {
    private String token;

    private AuthenticationResponse(AuthenticationResponseBuilder authenticationResponseBuilder) {
        this.token = authenticationResponseBuilder.token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class AuthenticationResponseBuilder {
        private String token;

        public AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(this);
        }
    }
}
