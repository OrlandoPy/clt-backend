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
@Table(name = "ROLES")
public class Rol {

    @Id
    @SequenceGenerator(name = "ROL_ID_GENERATOR", sequenceName = "ROL_ID_ROL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_ID_GENERATOR")
    @NotNull
    @Column(name = "ID_ROL")
    private Long id;
    @NotNull
    @Column(name = "NOMBRE")
    private String nombre;
}
