package com.cltbackend.service;

import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario saveUsuario(Usuario usuario);

    Usuario getUsuario(String username);

    List<Usuario> getUsuarios();

    Rol saveRol(Rol rol);

    void addRolToUsuario(String username, String nombreRol);
}
