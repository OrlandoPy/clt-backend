package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.model.Servicio;

public interface ServicioService {

    ResponseDTO saveServicio(Servicio servicio);

    ResponseDTO editServicio(Servicio servicio);

    ResponseDTO getServicios();
}
