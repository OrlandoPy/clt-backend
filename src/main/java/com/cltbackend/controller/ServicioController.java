package com.cltbackend.controller;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.model.Servicio;
import com.cltbackend.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/servicio")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping(value = "/listarServicios", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> getServicios() {
        return servicioService.getServicios().build();
    }

    @PostMapping(value = "/crearServicio", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> saveServicio(@RequestBody Servicio servicio) {
        return servicioService.saveServicio(servicio).build();
    }

    @PutMapping(value = "/actualizarServicio", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResponseDTO> editServicio(@RequestBody Servicio servicio) {
        return servicioService.editServicio(servicio).build();
    }

}
