package com.cltbackend.controller;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.dto.RolUsuarioDTO;
import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;
import com.cltbackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping(value = "/listarUsuarios", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok().body(usuarioService.getUsuarios());
    }

    @PostMapping(value = "/crearUsuario", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> saveUsuario(@RequestBody Usuario usuario) {
        return usuarioService.saveUsuario(usuario).build();
    }

    @PutMapping(value = "/actualizarUsuario", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> editUsuario(@RequestBody Usuario usuario) {
        return usuarioService.editUsuario(usuario).build();
    }

    @PostMapping(value = "/agregarSaldo", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> editUsuario(@RequestParam Long idUsuario, Long saldo) {
        return usuarioService.addSaldoToUsuario(idUsuario, saldo).build();
    }

    @PostMapping(value = "/crearRol", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> saveRol(@RequestBody Rol rol) {
        return usuarioService.saveRol(rol).build();
    }

    @GetMapping(value = "/listarRoles", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> getRoles() {
        return usuarioService.getRoles().build();
    }

    @PostMapping(value = "/asignarRolAUsuario", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> addRolToUsuario(@RequestBody RolUsuarioDTO rolUsuarioDTOList) {
       return usuarioService.addRolToUsuario(rolUsuarioDTOList).build();
    }

}
