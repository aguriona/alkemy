package com.backend.alkemy.controller;

import com.backend.alkemy.entity.Usuario;
import com.backend.alkemy.service.SendGridService;
import com.backend.alkemy.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/mail")
public class SendGridController {
    @Autowired
    SendGridService sendGridService;
    @Autowired
    UsuarioService usuarioService;


    @PostMapping("/send/{id}")
    public String send(@PathVariable Long id) throws IOException{
        Usuario user = usuarioService.listUsuarioById(id);
        return sendGridService.sendTextEmail(user);
    }
}
