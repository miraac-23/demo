package com.example.demo.dto.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
    private int userId;
}
