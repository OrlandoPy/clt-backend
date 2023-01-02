package com.cltbackend.repository;

import com.cltbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Optional<Usuario> findUsuarioByNroDocumento(String nroDocumento);
}
