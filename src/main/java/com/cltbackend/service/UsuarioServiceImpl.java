package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.dto.RolUsuarioDTO;
import com.cltbackend.dto.TableDTO;
import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;
import com.cltbackend.repository.RolRepository;
import com.cltbackend.repository.UsuarioRepository;
import com.cltbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public ResponseDTO saveUsuario(Usuario usuario) {

        if (usuario.getUsername() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El username es obligatorio", null);
        } else {
            Usuario existeUsuario = usuarioRepository.findByUsername(usuario.getUsername());
            if (existeUsuario != null) {
                return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario ya existe", null);
            }else if (!Util.validarCorreo(usuario.getUsername())) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El username no es válido, recuerde que este debe ser una dirección de correo electrónico", null);
        }
        }
        if (usuario.getPassword() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El password es obligatorio", null);
        }
        if (usuario.getNombre() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El nombre es obligatorio", null);
        }
        if (usuario.getNroDocumento() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El número de documento es obligatorio", null);
        }
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);
            return new ResponseDTO(new Date(), HttpStatus.OK, "El usuario fue creado con éxito", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la creación del Usuario", null);
        }
    }

    @Override
    public ResponseDTO editUsuario(Usuario usuario) {

        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());

            if (usuarioOptional.isPresent()) {
                usuarioOptional.map(usuarioMap -> {
                    usuarioMap.setUsername(usuario.getUsername());
                    usuarioMap.setNombre(usuario.getNombre());
                    usuarioMap.setNroDocumento(usuario.getNroDocumento());
                    return  usuarioRepository.save(usuario);
                });
                return new ResponseDTO(new Date(), HttpStatus.OK, "Usuario actualizado con éxito", null);
            } else {
                return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no existe", null);
            }
        } catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la actualización del usuario", null);
        }
    }

    @Override
    public ResponseDTO addSaldoToUsuario(Long idUsuario, Long saldo) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

            if (usuarioOptional.isPresent()) {
                usuarioOptional.map(usuarioMap -> {
                    usuarioMap.setSaldo(usuarioMap.getSaldo() + saldo);
                    return  usuarioRepository.save(usuarioOptional.get());
                });
                return new ResponseDTO(new Date(), HttpStatus.OK, "Saldo actualizado con éxito", null);
            } else {
                return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no existe", null);
            }
        } catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la actualización del saldo", null);
        }
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
    public ResponseDTO saveRol(Rol rol) {

        if (rol.getNombre() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El nombre es obligatorio", null);
        }
        try {
            rolRepository.save(rol);
            return new ResponseDTO(new Date(), HttpStatus.OK, "El Rol fue creado con éxito", null);
        } catch (Exception e) {
        e.printStackTrace();
        return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la creación del Usuario", null);
        }
    }

    @Override
    public ResponseDTO getRoles() {
        TableDTO<Rol> tableDTO = new TableDTO<>();
        List<Rol> roles = rolRepository.findAll();

        if (roles.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.NOT_FOUND, "No se han encontrado registros", null);
        }

        tableDTO.setLista(roles);
        tableDTO.setTotalRecords(roles.size());
        return new ResponseDTO(new Date(), HttpStatus.OK, "El listado de roles fue obtenido con éxito", tableDTO);
    }

    @Override
    public ResponseDTO addRolToUsuario (RolUsuarioDTO rolUsuarioDTO) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(rolUsuarioDTO.getIdUsuario());
        Usuario usuario;
        List<Rol> roles = new ArrayList<>();

        if (optionalUsuario.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no existe", null);
        } else if (rolUsuarioDTO.getIdRolList().isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "La lista de roles se encuentra vacía", null);
        }

        usuario = optionalUsuario.get();

        try {
            rolUsuarioDTO.getIdRolList().forEach(idRol -> {
                Optional<Rol> rol = rolRepository.findById(idRol);
                rol.ifPresent(roles::add);
            });
            usuario.setRoles(roles);
            usuarioRepository.save(usuario);
            return new ResponseDTO(new Date(), HttpStatus.OK, "Los Roles fueron asignados con éxito", null);
        } catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la asignación de roles", null);
        }
    }

}
