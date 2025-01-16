package com.login.service.dto;

public class JwtValidationDto {
    private boolean valid;
    private String username;

    public JwtValidationDto(boolean valid, String username) {
        this.valid = valid;
        this.username = username;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
