package com.proyecto.usuario_service.web.controller;

import com.proyecto.usuario_service.application.service.UsuarioService;
import com.proyecto.usuario_service.domain.model.Usuario;
import com.proyecto.usuario_service.domain.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorUsername(loginRequest.getUsername());
            if (usuario != null && passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login exitoso");
                response.put("role", usuario.getRole());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Usuario o contraseña incorrectos");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error durante la autenticación");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
