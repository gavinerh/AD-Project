package com.ad_project_android.model;

public class TokenJWT {
    private String jwt;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return jwt;
    }

    public void setToken(String token) {
        this.jwt = token;
    }
}
