package com.proyecto.usuario_service.domain.repository.impl;

import com.proyecto.usuario_service.application.service.UsuarioService;
import com.proyecto.usuario_service.domain.model.Usuario;
import com.proyecto.usuario_service.domain.repository.UsuarioRepository;
import com.proyecto.usuario_service.web.dto.UsuarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public boolean crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public boolean actualizarUsuario(int id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        modelMapper.getConfiguration().setPropertyCondition(context ->
                !(
                        context.getMapping().getLastDestinationProperty().getName().equals("password") &&
                                (context.getSource() == null || context.getSource().toString().trim().isEmpty())
                )
        );
        modelMapper.map(usuarioDTO, usuario);
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public boolean eliminarUsuario(int id) {
        if (!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuario no encontrado");
        usuarioRepository.deleteById(id);
        return true;
    }

    @Override
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
