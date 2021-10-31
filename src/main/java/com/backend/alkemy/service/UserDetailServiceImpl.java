package com.backend.alkemy.service;

import com.backend.alkemy.entity.Usuario;
import com.backend.alkemy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        UserBuilder userBuilder = null;
        if (usuario==null){
            throw new UsernameNotFoundException(username + " No encontrado");
        }
        userBuilder = User.withUsername(username);
        userBuilder.disabled(false);
        userBuilder.password(usuario.getPassword());
        userBuilder.authorities(getGrantedAuthorities(usuario));
        return userBuilder.build();
        // return new org.springframework.security.core.userdetails.User(usuario.getUsername(),usuario.getPassword(),getGrantedAuthorities(usuario));
    }

    private Collection<GrantedAuthority> getGrantedAuthorities (Usuario usuario){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (usuario.getRol().getNombre().equalsIgnoreCase("admin")){
           authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

}
