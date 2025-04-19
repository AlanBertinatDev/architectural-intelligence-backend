package com.architecture.backend_architecture.controller;


import com.architecture.backend_architecture.dto.*;
import com.architecture.backend_architecture.config.JwtUtils;
import com.architecture.backend_architecture.security.UsuarioDetailsImpl;
import com.architecture.backend_architecture.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Autenticación de usuarios")
public class AuthController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    @Operation(summary = "Registro de usuario")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        usuarioService.registrar(request);
        return ResponseEntity.ok("Usuario registrado correctamente. Inicie sesión para continuar.");
    }

    @PostMapping("/login")
    @Operation(summary = "Logueo de usuarios")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Extraemos el UsuarioDetailsImpl desde el Authentication
        UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) auth.getPrincipal();

        // Generamos el token usando UsuarioDetailsImpl
        String token = jwtUtils.generateToken(usuarioDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}