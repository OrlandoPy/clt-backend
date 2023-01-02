package com.cltbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PagoUsuarioDTO {

    private Long id;
    private DeudaUsuarioDTO deudaUsuario;
    private Long montoAbonado;
    private String nroReferenciaComprobante;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
}
