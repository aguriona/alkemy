package com.backend.alkemy.controller;

import com.backend.alkemy.dto.AuthenticationResponse;
import com.backend.alkemy.entity.Usuario;
import com.backend.alkemy.dto.AuthenticationRequest;
import com.backend.alkemy.service.UserDetailServiceImpl;
import com.backend.alkemy.service.UsuarioService;
import com.backend.alkemy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthentication(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        UserDetails usuario = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtUtil.generateToken(usuario);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody Usuario usuario){
        Usuario usuarioNew = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(usuarioNew, HttpStatus.ACCEPTED);
    }



}
