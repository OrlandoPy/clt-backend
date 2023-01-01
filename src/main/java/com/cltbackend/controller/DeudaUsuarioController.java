package com.cltbackend.controller;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.model.DeudaUsuario;
import com.cltbackend.service.DeudaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController()
@RequestMapping("/deudaUsuario")
@RequiredArgsConstructor
public class DeudaUsuarioController {

    private final DeudaUsuarioService deudaUsuarioService;

    @GetMapping(value = "/consultarDeudaUsuario", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> getServicios(@RequestParam Long idServicio, @RequestParam String nroDocumento) {
        return deudaUsuarioService.getDeudaUsuario(idServicio, nroDocumento).build();
    }

    @PostMapping(value = "/crearDeudaUsuario", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> saveServicio(@RequestBody DeudaUsuario deudaUsuario) {
        return deudaUsuarioService.saveDeudaUsuario(deudaUsuario).build();
    }

    @PutMapping(value = "/actualizarDeudaUsuario", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> editServicio(@RequestParam Long idServicio, @RequestParam Long idUsuario, @RequestParam Long deuda) {
        return deudaUsuarioService.updateDeudaUsuario(idServicio, idUsuario, deuda).build();
    }
}
