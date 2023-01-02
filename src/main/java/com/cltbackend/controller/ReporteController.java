package com.cltbackend.controller;

import com.cltbackend.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping(value = "/historialDePagosPorFecha")
    public ResponseEntity<?> historialDePagosPorFecha(@RequestParam String fechaDesde, @RequestParam String fechaHasta) {
        return reporteService.historialDePagosPorFecha(fechaDesde, fechaHasta);
    }
}
