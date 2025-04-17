package com.architecture.backend_architecture.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}