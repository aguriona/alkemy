package com.backend.alkemy.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "personajes")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String imagen;

    @NotEmpty(message = "Debe tener un nombre")
    private String nombre;
    private Integer edad;
    private Long peso;

    @NotEmpty(message = "Debe tener alguna historia")
    @Column(length = 300)
    private String historia;


    @ManyToMany(mappedBy = "personajes")
    private List<PeliSerie> peliSeries;


    public Personaje() {
    }

    public Personaje(String imagen, String nombre, Integer edad, Long peso, String historia) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.peliSeries = new ArrayList<>();
    }

    public void addPeli(PeliSerie peli){
        if (this.peliSeries == null)this.peliSeries = new ArrayList<>();
        this.peliSeries.add(peli);
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public List<PeliSerie> getPeliSerie() {
        return peliSeries;
    }

    public void setPeliSeries(List<PeliSerie> peliSeries) {
        this.peliSeries = peliSeries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
