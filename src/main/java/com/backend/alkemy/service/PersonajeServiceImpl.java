package com.backend.alkemy.service;

import com.backend.alkemy.dto.PersonajeDTO;
import com.backend.alkemy.dto.PersonajeDetailDTO;
import com.backend.alkemy.entity.PeliSerie;
import com.backend.alkemy.entity.Personaje;
import com.backend.alkemy.repository.PeliSerieRepository;
import com.backend.alkemy.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonajeServiceImpl implements IPersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;
    @Autowired
    private PeliSerieRepository peliSerieRepository;


    @Override
    @Transactional(readOnly = true)
    public List<PersonajeDTO> getPersonajeList() {
        List<Personaje> personajesList = personajeRepository.findAll();
        ArrayList<PersonajeDTO> personajeDTOList = new ArrayList<>();
        for (Personaje pers : personajesList) {
            PersonajeDTO personajeDTO = new PersonajeDTO();
            personajeDTO.setImagen(pers.getImagen());
            personajeDTO.setNombre(pers.getNombre());

            personajeDTOList.add(personajeDTO);
        }
        return personajeDTOList;
    }

    @Override
    public Personaje getPersonajebyId(Long id) {
        return personajeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonajeDetailDTO getPersonajeDetail(Long id) {
        Personaje personaje = personajeRepository.findById(id).get();
        PersonajeDetailDTO personajeDetailDto = new PersonajeDetailDTO();
        personajeDetailDto.setImagen(personaje.getImagen());
        personajeDetailDto.setNombre(personaje.getNombre());
        personajeDetailDto.setEdad(personaje.getEdad());
        personajeDetailDto.setPeso(personajeDetailDto.getPeso());
        personajeDetailDto.setHistoria(personaje.getHistoria());
        return personajeDetailDto;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PersonajeDTO> getPersonajeByNombre(String nombre) {
        List<Personaje> personajesList = personajeRepository.findByNombre(nombre);
        ArrayList<PersonajeDTO> personajeDTOList = new ArrayList<>();
        for (Personaje pers : personajesList) {
            PersonajeDTO personajeDTO = new PersonajeDTO();
            personajeDTO.setImagen(pers.getImagen());
            personajeDTO.setNombre(pers.getNombre());

            personajeDTOList.add(personajeDTO);
        }
        return personajeDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeDTO> getPersonajeByEdad(Integer edad) {
        List<Personaje> personajesList = personajeRepository.findByEdad(edad);
        ArrayList<PersonajeDTO> personajeDTOList = new ArrayList<>();
        for (Personaje pers : personajesList) {
            PersonajeDTO personajeDTO = new PersonajeDTO();
            personajeDTO.setImagen(pers.getImagen());
            personajeDTO.setNombre(pers.getNombre());

            personajeDTOList.add(personajeDTO);
        }
        return personajeDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeDTO> getPersonajeByPeliserie(Long id) {
        PeliSerie peliSerie = peliSerieRepository.findById(id).orElse(null);
        List<Personaje> personajesList = peliSerie.getPersonaje();
        ArrayList<PersonajeDTO> personajeDTOList = new ArrayList<>();
        for (Personaje pers : personajesList) {
            PersonajeDTO personajeDTO = new PersonajeDTO();
            personajeDTO.setImagen(pers.getImagen());
            personajeDTO.setNombre(pers.getNombre());

            personajeDTOList.add(personajeDTO);
        }
        return personajeDTOList;
    }

    @Override
    public Personaje savePersonaje(PersonajeDetailDTO personajeDetailDto) {
        Personaje personaje = new Personaje();

        personaje.setNombre(personajeDetailDto.getNombre());
        personaje.setEdad(personajeDetailDto.getEdad());
        personaje.setHistoria(personajeDetailDto.getHistoria());
        personaje.setPeso(personajeDetailDto.getPeso());
        personaje.setImagen(personajeDetailDto.getImagen());
        personaje.setPeliSeries(personajeDetailDto.getPeliSerieAsoc());

        return personajeRepository.save(personaje);
    }

    @Override
    public Personaje updatePersonaje(Long id, PersonajeDetailDTO personajeDetailDTO) {
        try {
            Personaje personajeOld = personajeRepository.findById(id).get();
            personajeOld.setNombre(personajeDetailDTO.getNombre());
            personajeOld.setEdad(personajeDetailDTO.getEdad());
            personajeOld.setHistoria(personajeDetailDTO.getHistoria());
            personajeOld.setPeso(personajeDetailDTO.getPeso());
            personajeOld.setImagen(personajeDetailDTO.getImagen());
            personajeOld.setPeliSeries(personajeDetailDTO.getPeliSerieAsoc());

            return personajeRepository.save(personajeOld);
        } catch (Exception e) {
            throw new NoSuchElementException("No se encontro el personaje");
        }
    }

    @Override
    public void deletePersonaje(Long id) {
        personajeRepository.deleteById(id);
    }

    @Override
    public boolean existPersonaje(Long id) {
        return personajeRepository.existsById(id);
    }


}
