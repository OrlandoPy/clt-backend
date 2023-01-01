package com.cltbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DEUDAS_USUARIOS")
public class DeudaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_DEUDA_USUARIO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICIO", referencedColumnName = "ID_SERVICIO", unique = true)
    @NotNull
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", unique = true)
    @NotNull
    private Usuario usuario;

    @Column(name = "MONTO_DEUDA_TOTAL")
    @NotNull
    private Long montoDeudaTotal;

    @Column(name = "MONTO_ABONADO")
    @NotNull
    private Long montoAbonado;
}
