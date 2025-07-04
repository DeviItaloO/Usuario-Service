package com.proyecto.usuario_service.web.controller;

import com.proyecto.usuario_service.application.service.UsuarioService;
import com.proyecto.usuario_service.web.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
            if (usuario != null) {
                return new ResponseEntity<>(usuario, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuarioDTO){
        try {
            boolean respuesta = usuarioService.crearUsuario(usuarioDTO);
            if (respuesta){
                return new ResponseEntity<>("Usuario creado correctamente", HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("Usuario no existe", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO){
        try {
            boolean respuesta = usuarioService.actualizarUsuario(id, usuarioDTO);
            if (respuesta){
                return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Usuario no existe", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id){
        try {
            boolean respuesta = usuarioService.eliminarUsuario(id);
            if (respuesta){
                return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
