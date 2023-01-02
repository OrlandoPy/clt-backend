package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.model.PagoUsuario;

import java.util.Date;

public interface PagoUsuarioService {

    ResponseDTO savePagoUsuario(Long idServicio ,String nroReferenciaComprobante, Long montoAbonado);

    ResponseDTO getPagosUsuarioByFecha(String fechaDesde, String fechaHasta, int page, int pageSize);

    ResponseDTO getPagosUsuarioByIdServicio(Long idServicio, int page, int pageSize);
}
