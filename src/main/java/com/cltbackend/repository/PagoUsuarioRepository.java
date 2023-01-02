package com.cltbackend.repository;

import com.cltbackend.model.PagoUsuario;
import com.cltbackend.model.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PagoUsuarioRepository extends PagingAndSortingRepository<PagoUsuario, Long>, JpaSpecificationExecutor<PagoUsuario> {

    Page<PagoUsuario> findPagoUsuarioByFechaBetween(Date fechaDesde, Date fechaHasta, Pageable pageable);

    Page<PagoUsuario> findPagoUsuarioByDeudaUsuario_Servicio(Servicio servicio, Pageable pageable);

}
