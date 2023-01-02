package com.cltbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @SequenceGenerator(name = "USUARIO_ID_GENERATOR", sequenceName = "USUARIO_ID_USUARIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_ID_GENERATOR")
    @NotNull
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "USERNAME")
    @NotNull
    @Email
    private String username;

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @NotNull
    @Column(name = "NOMBRE")
    private String nombre;

    @NotNull
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;

    @Column(name = "SALDO")
    private long saldo;

    @ManyToMany
    @JoinTable(name = "USUARIOS_ROLES", joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROL"))
    private List<Rol> roles;
}
