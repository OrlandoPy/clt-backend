package com.cltbackend.service;

import com.cltbackend.dto.*;
import com.cltbackend.model.DeudaUsuario;
import com.cltbackend.model.PagoUsuario;
import com.cltbackend.model.Servicio;
import com.cltbackend.model.Usuario;
import com.cltbackend.repository.DeudaUsuarioRepository;
import com.cltbackend.repository.PagoUsuarioRepository;
import com.cltbackend.repository.ServicioRepository;
import com.cltbackend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoUsuarioServiceImpl implements PagoUsuarioService{

    private final PagoUsuarioRepository pagoUsuarioRepository;
    private final DeudaUsuarioRepository deudaUsuarioRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ResponseDTO savePagoUsuario(Long idServicio, String nroReferenciaComprobante, Long montoAbonado) {

        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El servicio ingresado no existe", null);
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByNroDocumento(nroReferenciaComprobante);
        if (usuarioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no existe", null);
        }

        Optional<DeudaUsuario> deudaUsuarioOptional = deudaUsuarioRepository.findDeudaUsuarioByIdServicioByNroDocumento(idServicio, nroReferenciaComprobante);
        if (deudaUsuarioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no tiene deudas", null);
        } else if (deudaUsuarioOptional.get().getMontoDeudaTotal() <= deudaUsuarioOptional.get().getMontoAbonado()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El usuario no tiene deudas", null);
        } else if ((deudaUsuarioOptional.get().getMontoDeudaTotal() - deudaUsuarioOptional.get().getMontoAbonado()) < montoAbonado ) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, String.format("El monto a abonar es mayor a la deuda actual de %s", deudaUsuarioOptional.get().getMontoDeudaTotal() - deudaUsuarioOptional.get().getMontoAbonado()), null);
        } else if (montoAbonado > usuarioOptional.get().getSaldo()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El saldo del usuario no es el suficiente para realizar el pago", null);
        }

        try {
            usuarioOptional.map(usuarioMap -> {
                usuarioMap.setSaldo(usuarioMap.getSaldo() - montoAbonado);
                return usuarioRepository.save(usuarioMap);
            });

            deudaUsuarioOptional.map(deudaUsuarioMap -> {
                deudaUsuarioMap.setMontoAbonado(deudaUsuarioMap.getMontoAbonado() + montoAbonado);
                return deudaUsuarioRepository.save(deudaUsuarioMap);
            });

            PagoUsuario pagoUsuario = new PagoUsuario(
                    null,
                    deudaUsuarioOptional.get(),
                    montoAbonado,
                    nroReferenciaComprobante,
                    new Date()
            );
            pagoUsuarioRepository.save(pagoUsuario);
            return new ResponseDTO(new Date(), HttpStatus.OK, "El pago fue realizado con éxito", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado al realizar el pago", null);
        }
    }

    @Override
    public ResponseDTO getPagosUsuarioByFecha(String fechaDesde, String fechaHasta, int page, int pageSize) {

        try {
            Pageable paging = PageRequest.of(page, pageSize);
            Page<PagoUsuario> pagoUsuarioPage = pagoUsuarioRepository.findPagoUsuarioByFechaBetween(new SimpleDateFormat("dd/MM/yyyy").parse(fechaDesde), new SimpleDateFormat("dd/MM/yyyy").parse(fechaHasta), paging);

            if (pagoUsuarioPage.isEmpty()) {
                return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "Datos no encontrados", null);
            }

            List<PagoUsuarioDTO> pagoUsuarioDTOList = new ArrayList<>();
            pagoUsuarioPage.getContent().forEach(pagoUsuario -> {
                PagoUsuarioDTO pagoUsuarioDTO = new PagoUsuarioDTO(
                        pagoUsuario.getId(),
                        new DeudaUsuarioDTO(
                                pagoUsuario.getDeudaUsuario().getId(),
                                new ServicioDTO(
                                        pagoUsuario.getDeudaUsuario().getServicio().getId(),
                                        pagoUsuario.getDeudaUsuario().getServicio().getNombre(),
                                        pagoUsuario.getDeudaUsuario().getServicio().getDescripcion()
                                ),
                                new UsuarioDTO(
                                        pagoUsuario.getDeudaUsuario().getUsuario().getId(),
                                        pagoUsuario.getDeudaUsuario().getUsuario().getUsername(),
                                        pagoUsuario.getDeudaUsuario().getUsuario().getNombre(),
                                        pagoUsuario.getDeudaUsuario().getUsuario().getNroDocumento(),
                                        pagoUsuario.getDeudaUsuario().getUsuario().getSaldo(),
                                        null
                                ),
                                pagoUsuario.getDeudaUsuario().getMontoDeudaTotal(),
                                pagoUsuario.getDeudaUsuario().getMontoAbonado()
                        ),
                        pagoUsuario.getMontoAbonado(),
                        pagoUsuario.getNroReferenciaComprobante(),
                        pagoUsuario.getFecha()
                );
                pagoUsuarioDTOList.add(pagoUsuarioDTO);
            });

            TableDTO<PagoUsuarioDTO> tableDTO = new TableDTO<>();
            tableDTO.setLista(pagoUsuarioDTOList);
            tableDTO.setTotalRecords(pagoUsuarioPage.getTotalElements());
            return new ResponseDTO(new Date(), HttpStatus.OK, "Lista de Pagos del Usuario", tableDTO);
        }catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado al realizar la consulta de pagos", null);
        }
    }

    @Override
    public ResponseDTO getPagosUsuarioByIdServicio(Long idServicio, int page, int pageSize) {

        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isEmpty()) {
            return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "El servicio no existe", null);
        }

        try {
            Pageable paging = PageRequest.of(page, pageSize);
            Page<PagoUsuario> pagoUsuarioPage = pagoUsuarioRepository.findPagoUsuarioByDeudaUsuario_Servicio(servicioOptional.get(), paging);

            if (pagoUsuarioPage.isEmpty()) {
                return new ResponseDTO(new Date(), HttpStatus.BAD_REQUEST, "Datos no encontrados", null);
            }

            List<PagoUsuarioDTO> pagoUsuarioDTOList = new ArrayList<>();
            pagoUsuarioPage.getContent().forEach(pagoUsuario -> {
                PagoUsuarioDTO pagoUsuarioDTO = new PagoUsuarioDTO(
                    pagoUsuario.getId(),
                    new DeudaUsuarioDTO(
                        pagoUsuario.getDeudaUsuario().getId(),
                        new ServicioDTO(
                            pagoUsuario.getDeudaUsuario().getServicio().getId(),
                            pagoUsuario.getDeudaUsuario().getServicio().getNombre(),
                            pagoUsuario.getDeudaUsuario().getServicio().getDescripcion()
                        ),
                        new UsuarioDTO(
                                pagoUsuario.getDeudaUsuario().getUsuario().getId(),
                                pagoUsuario.getDeudaUsuario().getUsuario().getUsername(),
                                pagoUsuario.getDeudaUsuario().getUsuario().getNombre(),
                                pagoUsuario.getDeudaUsuario().getUsuario().getNroDocumento(),
                                pagoUsuario.getDeudaUsuario().getUsuario().getSaldo(),
                                null
                        ),
                        pagoUsuario.getDeudaUsuario().getMontoDeudaTotal(),
                        pagoUsuario.getDeudaUsuario().getMontoAbonado()
                    ),
                    pagoUsuario.getMontoAbonado(),
                    pagoUsuario.getNroReferenciaComprobante(),
                    pagoUsuario.getFecha()
                );
                pagoUsuarioDTOList.add(pagoUsuarioDTO);
            });

            TableDTO<PagoUsuarioDTO> tableDTO = new TableDTO<>();
            tableDTO.setLista(pagoUsuarioDTOList);
            tableDTO.setTotalRecords(pagoUsuarioPage.getTotalElements());
            return new ResponseDTO(new Date(), HttpStatus.OK, "Lista de Pagos del Usuario", tableDTO);
        } catch (Exception e) {
            return new ResponseDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado al realizar la consulta de pagos", null);
        }
    }
}
