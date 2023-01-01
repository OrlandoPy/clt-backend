package com.cltbackend.controller;

import com.cltbackend.DTO.RolUsuarioDTO;
import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;
import com.cltbackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<Usuario>> getUsuario() {
        return ResponseEntity.ok().body(usuarioService.getUsuarios());
    }

    @PostMapping("/crearUsuario")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/crearUsuario").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.saveUsuario(usuario));
    }

    @PostMapping("/crearRol")
    public ResponseEntity<Rol> saveRol(@RequestBody Rol rol) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/usuario/crearRol").toUriString());
        return ResponseEntity.created(uri).body(usuarioService.saveRol(rol));
    }

    @PostMapping("/asignarROlAUsuario")
    public ResponseEntity<?> addRolToUsuario(@RequestBody RolUsuarioDTO rolUsuarioDTO) {
        usuarioService.addRolToUsuario(rolUsuarioDTO.getUsername(), rolUsuarioDTO.getNombreRol());
        return ResponseEntity.ok().build();
    }


}
