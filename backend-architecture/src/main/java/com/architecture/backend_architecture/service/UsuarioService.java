package com.architecture.backend_architecture.service;

import com.architecture.backend_architecture.dto.RegisterRequest;
import com.architecture.backend_architecture.model.NombreRol;
import com.architecture.backend_architecture.model.Rol;
import com.architecture.backend_architecture.model.Usuario;
import com.architecture.backend_architecture.repository.RolRepository;
import com.architecture.backend_architecture.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    public Usuario registrar(RegisterRequest request) {
        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        // Buscar el rol en la base de datos usando el nombre del rol
        Rol rol = rolRepository.findByNombre(NombreRol.valueOf(request.getRol()))
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Asignar el rol al usuario (en este caso, solo un rol, pero podemos agregar más roles si fuera necesario)
        usuario.getRoles().add(rol);

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
}