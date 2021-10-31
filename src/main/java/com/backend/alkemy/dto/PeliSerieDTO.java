package com.backend.alkemy.dto;

import java.io.Serializable;
import java.util.Date;


public class PeliSerieDTO implements Serializable {

    private String imagen;
    private String titulo;
    private Date fechaCreacion;

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
}
