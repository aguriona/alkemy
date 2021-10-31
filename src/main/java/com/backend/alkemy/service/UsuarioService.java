package com.backend.alkemy.service;

import com.backend.alkemy.dto.Mensaje;
import com.backend.alkemy.entity.Usuario;
import com.backend.alkemy.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listUsuario() {
        return usuarioRepository.findAll();
    }

    public Usuario listUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
