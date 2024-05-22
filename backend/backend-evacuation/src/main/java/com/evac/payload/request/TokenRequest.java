package com.evac.payload.request;


import javax.validation.constraints.NotBlank;

/**
 * This class is a request to get the token from the frontend and then store it in the database
 */
public class TokenRequest {
    @NotBlank
    private String token;

    @NotBlank
    private String email;

    public TokenRequest(){}
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
