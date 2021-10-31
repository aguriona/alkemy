package com.backend.alkemy.dto;

import com.backend.alkemy.entity.Genero;
import com.backend.alkemy.entity.PeliSerie;

import java.io.Serializable;
import java.util.List;

public class PersonajeDetailDTO implements Serializable {

    private String imagen;
    private String nombre;
    private Integer edad;
    private Long peso;
    private String historia;
    private List<PeliSerie> peliSerieAsoc;


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

    public List<PeliSerie> getPeliSerieAsoc() {
        return peliSerieAsoc;
    }

    public void setPeliSerieAsoc(List<PeliSerie> peliSerieAsoc) {
        this.peliSerieAsoc = peliSerieAsoc;
    }
}
