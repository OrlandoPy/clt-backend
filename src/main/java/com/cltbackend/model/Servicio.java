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
@Table(name = "SERVICIOS")
public class Servicio {

    @Id
    @SequenceGenerator(name = "SERVICIO_ID_GENERATOR", sequenceName = "SERVICIO_ID_SERVICIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICIO_ID_GENERATOR")
    @NotNull
    @Column(name = "ID_SERVICIO")
    private Long id;

    @NotNull
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;
}
