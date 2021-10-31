package com.backend.alkemy.service;

import com.backend.alkemy.entity.Rol;
import com.backend.alkemy.entity.Usuario;
import com.backend.alkemy.repository.RolRepository;
import com.backend.alkemy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listRol() {
        return rolRepository.findAll();
    }

    public Rol findRolById(Long id) {
        return rolRepository.findById(id).get();
    }

    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public void delete(Long id) {
        try {
            rolRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
