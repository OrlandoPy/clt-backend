package com.cltbackend.controller;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.service.PagoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Date;

@RestController
@RequestMapping("/pagoUsuario")
@RequiredArgsConstructor
public class PagoUsuarioController {

    private final PagoUsuarioService pagoUsuarioService;

    @GetMapping(value = "/listarPagosUsuarioPorFecha", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> getPagoUsuarioByFecha(@RequestParam String fechaDesde, @RequestParam String fechaHasta, @RequestParam int page, @RequestParam int pageSize) {
        return pagoUsuarioService.getPagosUsuarioByFecha(fechaDesde, fechaHasta, page, pageSize).build();
    }

    @GetMapping(value = "/listarPagosUsuarioPorServicio", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> getPagoUsuarioByFecha(@RequestParam Long idServicio, @RequestParam int page, @RequestParam int pageSize) {
        return pagoUsuarioService.getPagosUsuarioByIdServicio(idServicio, page, pageSize).build();
    }

    @PostMapping(value = "/realizarPagoUsuario", produces= MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> saveServicio(@RequestParam Long idServicio, @RequestParam String nroReferenciaComprobante, @RequestParam Long montoAbonado) {
        return pagoUsuarioService.savePagoUsuario(idServicio, nroReferenciaComprobante, montoAbonado).build();
    }
}
