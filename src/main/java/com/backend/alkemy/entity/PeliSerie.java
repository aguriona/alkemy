package com.backend.alkemy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "peliseries")
public class PeliSerie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String imagen;

    @NotEmpty(message = "Debe tener un titulo")
    private String titulo;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Min(0)
    @Max(5)
    private Integer calificacion;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "personajes_peliseries",
            joinColumns = @JoinColumn(name = "peliserie_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id"))
    @JsonIgnoreProperties({"hibernateLazyInicializer", "handler"})
    private List<Personaje> personajes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id")
    @JsonIgnoreProperties({"hibernateLazyInicializer", "handler"})
    private Genero genero;

    public PeliSerie() {
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public List<Personaje> getPersonaje() {
        return personajes;
    }

    public void setPersonaje(List<Personaje> personaje) {
        this.personajes = personaje;
    }

    public PeliSerie(Long id, String imagen, String titulo, Date fechaCreacion, Integer calificacion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.personajes = new ArrayList<>();
    }
    public void setPersonajes(Personaje personaje){
        personajes.add(personaje);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
