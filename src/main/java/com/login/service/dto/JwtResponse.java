package com.login.service.dto;

import java.time.LocalDateTime;

public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private LocalDateTime accessTokenExpiryTime;
    private LocalDateTime refreshTokenExpiryTime;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String refreshToken, String username, LocalDateTime accessTokenExpiryTime, LocalDateTime refreshTokenExpiryTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.refreshTokenExpiryTime = refreshTokenExpiryTime;
        this.accessTokenExpiryTime = accessTokenExpiryTime;
    }

    public LocalDateTime getRefreshTokenExpiryTime() {
        return refreshTokenExpiryTime;
    }

    public void setRefreshTokenExpiryTime(LocalDateTime refreshTokenExpiryTime) {
        this.refreshTokenExpiryTime = refreshTokenExpiryTime;
    }

    public LocalDateTime getAccessTokenExpiryTime() {
        return accessTokenExpiryTime;
    }

    public void setAccessTokenExpiryTime(LocalDateTime accessTokenExpiryTime) {
        this.accessTokenExpiryTime = accessTokenExpiryTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
