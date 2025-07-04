package com.proyecto.usuario_service.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private int idUsuario;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
