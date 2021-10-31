package com.backend.alkemy.repository;

import com.backend.alkemy.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    public List<Personaje> findByNombre(String nombre);

    public List<Personaje> findByEdad(Integer edad);

//    @Query("SELECT u FROM personajes_peliseries  WHERE peliserie_id = ?1")
//    public List<Personaje> findByPeliSerie(Long id);

}
