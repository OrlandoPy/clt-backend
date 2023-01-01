package com.cltbackend.service;

import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;
import com.cltbackend.repository.RolRepository;
import com.cltbackend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usuario.getRoles().forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
        });
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario getUsuario(String username) {

        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> getUsuarios() {

        return usuarioRepository.findAll();
    }

    @Override
    public Rol saveRol(Rol rol) {

        return rolRepository.save(rol);
    }

    @Override
    public void addRolToUsuario(String username, String nombreRol) {

        Usuario  usuario = usuarioRepository.findByUsername(username);
        Rol rol = rolRepository.findByNombre(nombreRol);
        usuario.getRoles().add(rol);
    }

}
