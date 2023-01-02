package com.cltbackend.service;

import org.springframework.http.ResponseEntity;

public interface ReporteService {

    ResponseEntity<?> historialDePagosPorFecha(String fechaDesde, String fechaHasta);

}
