package com.example.Travel_mgmt_minor.dto;

// dto/RegisterRequest.java

import lombok.Data;



import lombok.Data;

public class RegisterRequest {
    private String username;
    private String password;
    private String role;  // e.g. "ROLE_TRAVEL_COMPANY" or "ROLE_USER"

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

