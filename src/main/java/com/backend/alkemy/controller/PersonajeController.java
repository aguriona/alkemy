package com.backend.alkemy.controller;

import com.backend.alkemy.dto.Mensaje;
import com.backend.alkemy.dto.PersonajeDTO;
import com.backend.alkemy.dto.PersonajeDetailDTO;
import com.backend.alkemy.entity.Personaje;
import com.backend.alkemy.service.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/characters")
public class PersonajeController {

    @Autowired
    IPersonajeService personajeService;

    @GetMapping
    public ResponseEntity<?> listarPersonajes(@RequestParam(value = "name", required = false) String nombre,
                                              @RequestParam(value = "age", required = false) Integer edad,
                                              @RequestParam(value = "movieId", required = false) Long movieId) {

        if (nombre != null && nombre.length() > 0) {
            List<PersonajeDTO> personajeDTOList = new ArrayList<>();
            try {
                personajeDTOList = personajeService.getPersonajeByNombre(nombre);
                if (personajeDTOList.isEmpty())
                    return new ResponseEntity<>(new Mensaje("Nombre Incorrecto"), HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<PersonajeDTO>>(personajeDTOList, HttpStatus.OK);
        }

        if (edad != null && edad > 0) {
            List<PersonajeDTO> personajeDTOList = new ArrayList<>();
            try {
                personajeDTOList = personajeService.getPersonajeByEdad(edad);
                if (personajeDTOList.isEmpty())
                    return new ResponseEntity<>(new Mensaje("No hay personajes de esa edad"), HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<PersonajeDTO>>(personajeDTOList, HttpStatus.OK);
        }

        if (movieId != null && movieId > 0) {
            List<PersonajeDTO> personajeDTOList = new ArrayList<>();
            try {
                personajeDTOList = personajeService.getPersonajeByPeliserie(movieId);
                if (personajeDTOList.isEmpty())
                    return new ResponseEntity<>(new Mensaje("No hay personajes en esa peli o serie"), HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<PersonajeDTO>>(personajeDTOList, HttpStatus.OK);
        }

        return new ResponseEntity<List<PersonajeDTO>>(personajeService.getPersonajeList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> personajeDetail(@PathVariable Long id) {
        PersonajeDetailDTO personajeDetailDto;
        try {
            if (!personajeService.existPersonaje(id)){
                return new ResponseEntity<>(new Mensaje("Id Incorrecto"), HttpStatus.NOT_FOUND);}
            personajeDetailDto = personajeService.getPersonajeDetail(id);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<PersonajeDetailDTO>(personajeDetailDto, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePersonaje(@Valid @RequestBody PersonajeDetailDTO personajeDetailDto, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Personaje personajeNew = new Personaje();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "Error en el campo: " + err.getField() + ": " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());
            response.put("Error", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            personajeNew = personajeService.savePersonaje(personajeDetailDto);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar insert en BD");
            response.put("tipo de error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Personaje Creado");
        response.put("cliente", personajeNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updatePersonaje(@Valid @RequestBody PersonajeDetailDTO personajeDetailDTO,
                                             BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Personaje personajeOld = personajeService.getPersonajebyId(id);

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> {
                        return "Error en el campo: " + err.getField() + ": " + err.getDefaultMessage();
                    })
                    .collect(Collectors.toList());
            response.put("Error", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (personajeOld == null)
            return new ResponseEntity<>(new Mensaje("No exite el personaje"), HttpStatus.NOT_FOUND);

        try {
            personajeService.updatePersonaje(id, personajeDetailDTO);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error al actualizar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Mensaje("Personaje Actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePersonaje(@PathVariable Long id) {
        try {
            personajeService.deletePersonaje(id);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error" + e.getMostSpecificCause()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Mensaje("Personaje Eliminado"), HttpStatus.OK);
    }


}
