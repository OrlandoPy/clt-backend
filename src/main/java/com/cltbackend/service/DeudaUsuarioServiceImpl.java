package com.cltbackend.service;

import com.cltbackend.dto.DeudaUsuarioDTO;
import com.cltbackend.dto.ResponseDTO;
import com.cltbackend.dto.ServicioDTO;
import com.cltbackend.dto.UsuarioDTO;
import com.cltbackend.model.DeudaUsuario;
import com.cltbackend.model.Servicio;
import com.cltbackend.model.Usuario;
import com.cltbackend.repository.DeudaUsuarioRepository;
import com.cltbackend.repository.ServicioRepository;
import com.cltbackend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeudaUsuarioServiceImpl implements DeudaUsuarioService{

    private final DeudaUsuarioRepository deudaUsuarioRepository;

    private final UsuarioRepository usuarioRepository;

    private final ServicioRepository servicioRepository;

    @Override
    public ResponseDTO saveDeudaUsuario(DeudaUsuario deudaUsuario) {

        if (deudaUsuario.getUsuario().getId() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario es obligatorio", null);
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(deudaUsuario.getUsuario().getId());
        if (usuarioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no existe", null);
        }

        if (deudaUsuario.getServicio().getId() == null) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El servicio es obligatorio", null);
        }

        Optional<Servicio> servicioOptional = servicioRepository.findById(deudaUsuario.getServicio().getId());
        if (servicioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El servicio no existe", null);
        }

        if (deudaUsuario.getMontoDeudaTotal() <= 0) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El monto deuda total deber ser mayor a cero", null);

        }

        if (deudaUsuario.getMontoAbonado() < 0) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El monto abonado deber ser mayor o igual a cero", null);

        }

        Optional<DeudaUsuario> deudaUsuarioDB = deudaUsuarioRepository.findDeudaUsuarioByServicioAndUsuario(servicioOptional.get(), usuarioOptional.get());
        if (deudaUsuarioDB.isPresent()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "La deuda ya existe", null);
        }

        try {
            deudaUsuario.setUsuario(usuarioOptional.get());
            deudaUsuario.setServicio(servicioOptional.get());
            deudaUsuarioRepository.save(deudaUsuario);
            return new ResponseDTO(new Date(), HttpStatus.OK, "La deuda del usuario fue creada con éxito", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la creación de la deuda del usuario", null);
        }
    }

    @Override
    public ResponseDTO updateDeudaUsuario(Long idServicio, Long idUsuario, Long deuda) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no existe", null);
        }

        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El servicio no existe", null);
        }

        Optional<DeudaUsuario> deudaUsuario = deudaUsuarioRepository.findDeudaUsuarioByServicioAndUsuario(servicioOptional.get(), usuarioOptional.get());
        if (deudaUsuario.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "La deuda no existe", null);
        }

        try {
            deudaUsuario.map(deudaUsuarioMap -> {
                deudaUsuarioMap.setMontoDeudaTotal(deudaUsuarioMap.getMontoDeudaTotal() + deuda);
                return deudaUsuarioRepository.save(deudaUsuarioMap);
            });
            return new ResponseDTO(new Date(), HttpStatus.OK, "La deuda del usuario fue actualizada con éxito", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en la actualización de la deuda del usuario", null);
        }
    }

    @Override
    public ResponseDTO getDeudaUsuario(Long idServicio, String nroDocumento) {

        Optional<DeudaUsuario> deudaUsuarioOptional = deudaUsuarioRepository.findDeudaUsuarioByIdServicioByNroDocumento(idServicio, nroDocumento);
        if (deudaUsuarioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no tiene deudas pendientes", null);
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                deudaUsuarioOptional.get().getUsuario().getId(),
                deudaUsuarioOptional.get().getUsuario().getUsername(),
                deudaUsuarioOptional.get().getUsuario().getNombre(),
                deudaUsuarioOptional.get().getUsuario().getNroDocumento(),
                deudaUsuarioOptional.get().getUsuario().getSaldo(),
                null
        );

        ServicioDTO servicioDTO = new ServicioDTO(
                deudaUsuarioOptional.get().getServicio().getId(),
                deudaUsuarioOptional.get().getServicio().getNombre(),
                deudaUsuarioOptional.get().getServicio().getDescripcion()
        );

        DeudaUsuarioDTO deudaUsuarioDTO = new DeudaUsuarioDTO(
                deudaUsuarioOptional.get().getId(),
                servicioDTO,
                usuarioDTO,
                deudaUsuarioOptional.get().getMontoDeudaTotal(),
                deudaUsuarioOptional.get().getMontoAbonado()
        );

        return new ResponseDTO(new Date(), HttpStatus.OK, "Deuda del usuario", deudaUsuarioDTO);
    }
}
