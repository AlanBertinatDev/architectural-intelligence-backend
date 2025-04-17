package com.architecture.backend_architecture.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String username;
    private String email;
    private String password;
    private String rol; // ADMIN o EMPLEADO
}