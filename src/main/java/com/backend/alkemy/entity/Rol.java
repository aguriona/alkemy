package com.backend.alkemy.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Rol {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String nombre;

    @OneToMany(mappedBy = "rol",targetEntity = Usuario.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Usuario> usuarios;

    public Rol(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Rol() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
