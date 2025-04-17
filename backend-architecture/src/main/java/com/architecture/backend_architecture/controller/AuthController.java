package com.architecture.backend_architecture.controller;


import com.architecture.backend_architecture.dto.*;
import com.architecture.backend_architecture.config.JwtUtils;
import com.architecture.backend_architecture.security.UsuarioDetailsImpl;
import com.architecture.backend_architecture.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import com.architecture.backend_architecture.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        usuarioService.registrar(request);
        return ResponseEntity.ok("Usuario registrado correctamente. Inicie sesión para continuar.");
    }

    @PostMapping("/login")
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