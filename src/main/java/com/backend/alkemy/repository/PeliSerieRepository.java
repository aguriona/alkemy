package com.backend.alkemy.repository;

import com.backend.alkemy.entity.PeliSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliSerieRepository extends JpaRepository<PeliSerie, Long> {

    public List<PeliSerie> findByTitulo(String titulo);

    public List<PeliSerie> findByGenero(Long id);
}
