package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.model.DeudaUsuario;

public interface DeudaUsuarioService {

    ResponseDTO saveDeudaUsuario(DeudaUsuario deudaUsuario);

    ResponseDTO updateDeudaUsuario(Long idServicio, Long idUsuario,Long deuda);

    ResponseDTO getDeudaUsuario(Long idServicio, String nroDocumento);
}
