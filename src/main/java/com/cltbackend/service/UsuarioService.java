package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.dto.RolUsuarioDTO;
import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;

import java.util.List;

public interface UsuarioService {

    ResponseDTO saveUsuario(Usuario usuario);

    ResponseDTO editUsuario(Usuario usuario);

    ResponseDTO addSaldoToUsuario(Long idUsuario, Long saldo);

    Usuario getUsuario(String username);

    List<Usuario> getUsuarios();

    ResponseDTO saveRol(Rol rol);

    ResponseDTO getRoles();

    ResponseDTO addRolToUsuario(RolUsuarioDTO rolUsuarioDTO);
}
