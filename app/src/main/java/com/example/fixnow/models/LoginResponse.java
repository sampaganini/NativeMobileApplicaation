package com.example.fixnow.models;

public class LoginResponse {
    private String token;
    private String type;
    private Long userId;

    public String getToken() {
        return token;
    }

    public void setToken(String jwttoken) {
        this.token = jwttoken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
