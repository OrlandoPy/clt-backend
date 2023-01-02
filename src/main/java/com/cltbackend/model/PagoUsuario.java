package com.cltbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAGOS_USUARIOS")
public class PagoUsuario {

    @Id
    @SequenceGenerator(name = "PAGOS_USUARIOS_ID_GENERATOR", sequenceName = "PAGOS_USUARIOS_ID_PAGOS_USUARIOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAGOS_USUARIOS_ID_GENERATOR")
    @NotNull
    @Column(name = "ID_PAGO_USUARIO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DEUDA_USUARIO", referencedColumnName = "ID_DEUDA_USUARIO")
    @NotNull
    private DeudaUsuario deudaUsuario;

    @Column(name = "MONTO_ABONADO")
    @NotNull
    private Long montoAbonado;

    @Column(name = "NRO_REFERENCIA_COMPROBANTE")
    @NotNull
    private String nroReferenciaComprobante;

    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private Date fecha;
}