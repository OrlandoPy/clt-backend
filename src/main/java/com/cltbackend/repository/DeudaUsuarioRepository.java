package com.cltbackend.repository;

import com.cltbackend.model.DeudaUsuario;
import com.cltbackend.model.Servicio;
import com.cltbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeudaUsuarioRepository extends JpaRepository<DeudaUsuario, Long> {

    @Query("select d from DeudaUsuario d where d.servicio.id = ?1 and d.usuario.nroDocumento = ?2")
    Optional<DeudaUsuario> findDeudaUsuarioByIdServicioByNroDocumento(Long idServicio, String nroDocumento);

    Optional<DeudaUsuario> findDeudaUsuarioByServicioAndUsuario(Servicio servicio, Usuario usuario);
}
