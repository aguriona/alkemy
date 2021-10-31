package com.backend.alkemy.service;

import com.backend.alkemy.dto.PeliSerieDTO;
import com.backend.alkemy.dto.PeliSerieDetailDTO;
import com.backend.alkemy.entity.PeliSerie;
import com.backend.alkemy.repository.PeliSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PeliSerieServiceImpl implements IPeliSerieService {

    @Autowired
    private PeliSerieRepository peliSerieRepository;

    @Override
    public List<PeliSerie> findAlll() {
        return peliSerieRepository.findAll();
    }

    @Override
    public List<PeliSerieDTO> findByTitulo(String titulo) {

        List<PeliSerieDTO> peliSerieDTOList = new ArrayList<>();
        List<PeliSerie> peliSerieList = peliSerieRepository.findByTitulo(titulo);
        for (PeliSerie peliSerie : peliSerieList) {
            PeliSerieDTO peliSerieDTO = new PeliSerieDTO();
            peliSerieDTO.setImagen(peliSerie.getImagen());
            peliSerieDTO.setTitulo(peliSerie.getTitulo());
            peliSerieDTO.setFechaCreacion(peliSerie.getFechaCreacion());
            peliSerieDTOList.add(peliSerieDTO);
        }
        return peliSerieDTOList;
    }

    @Override
    public PeliSerieDetailDTO peliserieDetail(Long id) {
        PeliSerie peliSerie = peliSerieRepository.findById(id).get();
        PeliSerieDetailDTO peliSerieDetailDTO = new PeliSerieDetailDTO();

        peliSerieDetailDTO.setFechaCreacion(peliSerie.getFechaCreacion());
        peliSerieDetailDTO.setImagen(peliSerie.getImagen());
        peliSerieDetailDTO.setTitulo(peliSerie.getTitulo());
        peliSerieDetailDTO.setPersonajesAsociados(peliSerie.getPersonaje());

        return peliSerieDetailDTO;
    }

    @Override
    public List<PeliSerie> orderByFechaCreacion(String orden) {

        if (orden.equals("ASC")) {
            List<PeliSerie> peliSerieList = peliSerieRepository.findAll(Sort.by(Sort.Direction.ASC, "fechaCreacion"));
            return peliSerieList;
        }
        if (orden.equals("DESC")) {
            List<PeliSerie> peliSerieList = peliSerieRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacion"));
            return peliSerieList;
        }
        return null;
    }

    @Override
    public List<PeliSerieDTO> findByGenero(Long id) {
        List<PeliSerie> peliSerieList = peliSerieRepository.findAll();
        List<PeliSerie> peliSerieFiltrada = peliSerieList.stream()
                .filter(peSe -> peSe.getGenero().getId() == id)
                .collect(Collectors.toList());

        List<PeliSerieDTO> peliSeriebyGeneroList = new ArrayList<>();
        PeliSerieDTO peliSerieDTO = new PeliSerieDTO();
        for (PeliSerie peSe : peliSerieFiltrada) {

            peliSerieDTO.setTitulo(peSe.getTitulo());
            peliSerieDTO.setImagen(peSe.getImagen());
            peliSerieDTO.setFechaCreacion(peSe.getFechaCreacion());

            peliSeriebyGeneroList.add(peliSerieDTO);
        }
        return peliSeriebyGeneroList;
    }

    @Override
    public PeliSerie savePeliSerie(PeliSerieDetailDTO peliSerieDetailDTO) {
        PeliSerie peliSerieNew = new PeliSerie();

        peliSerieNew.setTitulo(peliSerieDetailDTO.getTitulo());
        peliSerieNew.setImagen(peliSerieDetailDTO.getImagen());
        peliSerieNew.setCalificacion(peliSerieDetailDTO.getCalificacion());
        peliSerieNew.setFechaCreacion(peliSerieDetailDTO.getFechaCreacion());
        peliSerieNew.setGenero(peliSerieDetailDTO.getGenero());
        peliSerieNew.setPersonaje(peliSerieDetailDTO.getPersonajesAsociados());


        return peliSerieRepository.save(peliSerieNew);
    }

    @Override
    public PeliSerie updatePeliserie(Long id, PeliSerieDetailDTO peliSerieDetailDTO) {

        try {
            PeliSerie peliSerieOld = peliSerieRepository.findById(id).orElse(null);

            peliSerieOld.setTitulo(peliSerieDetailDTO.getTitulo());
            peliSerieOld.setPersonaje(peliSerieDetailDTO.getPersonajesAsociados());
            peliSerieOld.setFechaCreacion(peliSerieDetailDTO.getFechaCreacion());
            peliSerieOld.setCalificacion(peliSerieDetailDTO.getCalificacion());
            peliSerieOld.setImagen(peliSerieDetailDTO.getImagen());
            peliSerieOld.setGenero(peliSerieDetailDTO.getGenero());

            return peliSerieRepository.save(peliSerieOld);
        } catch (Exception e) {
            throw new NoSuchElementException("Error, no se encontro");
        }
    }

    @Override
    public void deletePeliserie(Long id) {
        peliSerieRepository.deleteById(id);
    }

    public PeliSerie getPeliSerieById(Long id){
        return peliSerieRepository.findById(id).orElse(null);
    }
    public boolean existPeliSerie(Long id) {
        return peliSerieRepository.existsById(id);
    }
}
