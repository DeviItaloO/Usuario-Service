package com.proyecto.usuario_service.application.service;

import com.proyecto.usuario_service.domain.model.Usuario;
import com.proyecto.usuario_service.web.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO obtenerUsuarioPorId(int id);
    boolean crearUsuario(UsuarioDTO usuarioDTO);
    boolean actualizarUsuario(int id, UsuarioDTO usuarioDTO);
    boolean eliminarUsuario(int id);
    Usuario obtenerUsuarioPorUsername(String username);
}
