package com.cltbackend.service;

import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.dto.TableDTO;
import com.cltbackend.model.Servicio;
import com.cltbackend.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService{

    private final ServicioRepository servicioRepository;

    @Override
    public ResponseDTO saveServicio(Servicio servicio) {

        if (servicio.getNombre() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El nombre del servicio es obligatorio", null);
        }
        try {
            servicioRepository.save(servicio);
            return new ResponseDTO(new Date(), HttpStatus.OK, "Servicio creado con éxito", null);
        } catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la creación del servicio", null);
        }
    }

    @Override
    public ResponseDTO editServicio(Servicio servicio) {

        Optional<Servicio> servicioOptional = servicioRepository.findById(servicio.getId());

        if (servicioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El servicio no existe", null);
        }

        try {
            servicioOptional.map(servicioMap -> {
                servicioMap.setNombre(servicio.getNombre());
                servicioMap.setDescripcion(servicio.getDescripcion());
                return servicioRepository.save(servicioOptional.get());
            });
            return new ResponseDTO(new Date(), HttpStatus.OK, "Servicio actualizado con éxito", null);
        } catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la actualización del servicio", null);
        }
    }

    @Override
    public ResponseDTO getServicios() {
        TableDTO<Servicio> tableDTO = new TableDTO<>();
        List<Servicio> servicioList = servicioRepository.findAll();

        if (servicioList.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.NOT_FOUND, "No se han encontrado registros", null);
        }

        tableDTO.setLista(servicioList);
        tableDTO.setTotalRecords(servicioList.size());
        return new ResponseDTO(new Date(), HttpStatus.OK, "El listado de servicios fue obtenido con éxito", tableDTO);
    }

}
