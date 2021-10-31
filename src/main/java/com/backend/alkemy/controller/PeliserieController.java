package com.backend.alkemy.controller;

import com.backend.alkemy.dto.Mensaje;
import com.backend.alkemy.dto.PeliSerieDTO;
import com.backend.alkemy.dto.PeliSerieDetailDTO;
import com.backend.alkemy.entity.PeliSerie;
import com.backend.alkemy.service.PeliSerieServiceImpl;
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
@RequestMapping("/movies")
public class PeliserieController {

    @Autowired
    PeliSerieServiceImpl peliSerieService;

    @GetMapping
    public ResponseEntity<?> listarPeliSerie(@RequestParam(value = "name", required = false) String titulo,
                                             @RequestParam(value = "order", required = false) String orden,
                                             @RequestParam(value = "generoId", required = false) Long idGenero) {
        if (titulo != null && titulo.length() > 0) {
            List<PeliSerieDTO> peliSerieDTOList = new ArrayList<>();
            try {
                peliSerieDTOList = peliSerieService.findByTitulo(titulo);
                if (peliSerieDTOList.isEmpty())
                    return new ResponseEntity<>(new Mensaje("No existen con ese titulo"), HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<PeliSerieDTO>>(peliSerieDTOList, HttpStatus.OK);
        }
        if (orden != null && orden.length() > 0) {
            List<PeliSerie> peliSerieList = new ArrayList<>();
            try {
                peliSerieList = peliSerieService.orderByFechaCreacion(orden);
                if (peliSerieList.isEmpty())
                    return new ResponseEntity<>(new Mensaje("Termino incorrecto"), HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<PeliSerie>>(peliSerieList, HttpStatus.OK);
        }
        if (idGenero != null && idGenero > 0) {
            List<PeliSerieDTO> peliSerieList = new ArrayList<>();
            try {
                peliSerieList = peliSerieService.findByGenero(idGenero);
                if (peliSerieList.isEmpty())
                    return new ResponseEntity<>(new Mensaje("Genero incorrecto"), HttpStatus.NOT_FOUND);
            } catch (DataAccessException e) {
                return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<PeliSerieDTO>>(peliSerieList, HttpStatus.OK);
        }

        return new ResponseEntity<List<PeliSerie>>(peliSerieService.findAlll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> peliserieDetail(@PathVariable Long id) {
        PeliSerieDetailDTO peliSerieDetailDTO;
        try {
            if (!peliSerieService.existPeliSerie(id)) {
                return new ResponseEntity<>(new Mensaje("Id Incorrecto"), HttpStatus.NOT_FOUND);
            }

            peliSerieDetailDTO = peliSerieService.peliserieDetail(id);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(peliSerieDetailDTO, HttpStatus.OK);
    }

    @GetMapping("/list/orderbydate")
    public ResponseEntity<?> orderByFechaCreacion(@RequestParam String orden) {
        List<PeliSerie> peliSerieList;
        try {
            peliSerieList = peliSerieService.orderByFechaCreacion(orden);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<PeliSerie>>(peliSerieList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePeliSerie(@Valid @RequestBody PeliSerieDetailDTO peliSerieDetailDTO, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        PeliSerie peliSerie = new PeliSerie();

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
            peliSerie = peliSerieService.savePeliSerie(peliSerieDetailDTO);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar insert en BD");
            response.put("tipo de error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Peli O Serie Creada");
        response.put("Peli o Serie:", peliSerie);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updatePeliSerie(@Valid @RequestBody PeliSerieDetailDTO peliSerieDetailDTO,
                                             BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        PeliSerie peliSerieOld = peliSerieService.getPeliSerieById(id);

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

        if (peliSerieOld == null)
            return new ResponseEntity<>(new Mensaje("No exite la peli o serie"), HttpStatus.NOT_FOUND);

        try {
            peliSerieService.updatePeliserie(id, peliSerieDetailDTO);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error al actualizar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Mensaje("Peli o Serie Actualizada"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePeliSerie(@PathVariable Long id) {
        try {
            peliSerieService.deletePeliserie(id);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(new Mensaje("Error" + e.getMostSpecificCause()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Mensaje("Peli o Serie Eliminada"), HttpStatus.OK);
    }

}
