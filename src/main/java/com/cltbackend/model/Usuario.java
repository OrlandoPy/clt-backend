package com.cltbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_USUARIO")
    private Long id;
    @NotNull
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "USERNAME")
    @NotNull
    private String username;
    @Column(name = "PASSWORD")
    @NotNull
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Rol> roles = new ArrayList<>();
}
