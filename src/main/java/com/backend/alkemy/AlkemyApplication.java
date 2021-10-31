package com.backend.alkemy;

import com.backend.alkemy.entity.Rol;
import com.backend.alkemy.entity.Usuario;
import com.backend.alkemy.service.RolService;
import com.backend.alkemy.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class AlkemyApplication implements CommandLineRunner {
	@Autowired
	RolService rolService;
	@Autowired
	UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(AlkemyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		rolService.saveRol(new Rol(1L, "admin"));
		rolService.saveRol(new Rol(2L, "user"));

		Usuario user1 = new Usuario();
		user1.setUsername("admin");
		user1.setPassword(new BCryptPasswordEncoder().encode("admin"));
		user1.setEmail("admin@mail");
		user1.setRol(rolService.findRolById(1L));
		usuarioService.saveUsuario(user1);

		Usuario user2 = new Usuario();
		user1.setUsername("user");
		user1.setPassword(new BCryptPasswordEncoder().encode("user"));
		user1.setEmail("user@mail");
		user1.setRol(rolService.findRolById(2L));
		usuarioService.saveUsuario(user2);
	}
}
