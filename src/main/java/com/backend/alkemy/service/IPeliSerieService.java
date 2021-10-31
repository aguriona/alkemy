package com.backend.alkemy.service;

import com.backend.alkemy.dto.PeliSerieDTO;
import com.backend.alkemy.dto.PeliSerieDetailDTO;
import com.backend.alkemy.entity.PeliSerie;

import java.util.List;

public interface IPeliSerieService {

    public List<PeliSerie> findAlll();

    public List<PeliSerieDTO> findByTitulo (String titulo);

    public PeliSerieDetailDTO peliserieDetail (Long id);

    public List<PeliSerie> orderByFechaCreacion (String orden);

    public List<PeliSerieDTO> findByGenero(Long id);

    public PeliSerie savePeliSerie (PeliSerieDetailDTO peliSerieDetailDTO);

    public PeliSerie updatePeliserie (Long id, PeliSerieDetailDTO peliSerieDetailDTO);

    public void deletePeliserie (Long id);

    public boolean existPeliSerie(Long id);


}
