package com.backend.alkemy.service;

import com.backend.alkemy.dto.PersonajeDTO;
import com.backend.alkemy.dto.PersonajeDetailDTO;
import com.backend.alkemy.entity.Personaje;

import java.util.List;

public interface IPersonajeService {

    public List<PersonajeDTO> getPersonajeList();

    public Personaje getPersonajebyId(Long id);

    public PersonajeDetailDTO getPersonajeDetail(Long id);

    public List<PersonajeDTO> getPersonajeByNombre(String nombre);

    public List<PersonajeDTO> getPersonajeByEdad(Integer edad);

    public List<PersonajeDTO> getPersonajeByPeliserie(Long id);

    public Personaje savePersonaje(PersonajeDetailDTO personajeDetailDto);

    public Personaje updatePersonaje(Long id, PersonajeDetailDTO personajeDetailDTO);

    public void deletePersonaje(Long id);

    public boolean existPersonaje (Long id);

}
