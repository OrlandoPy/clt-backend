package com.cltbackend;

import com.cltbackend.model.Rol;
import com.cltbackend.model.Usuario;
import com.cltbackend.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class CltBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CltBackendApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	CommandLineRunner run (UsuarioService usuarioService) {
		return args -> {
			usuarioService.saveRol(new Rol(null, "ROL_USER"));
			usuarioService.saveRol(new Rol(null, "ROL_MANAGER"));
			usuarioService.saveRol(new Rol(null, "ROL_ADMIN"));
			usuarioService.saveRol(new Rol(null, "ROL_SUPER_ADMIN"));

			usuarioService.saveUsuario(new Usuario(null, "Orlando Gomez", "ogomez", "12345", new ArrayList<>()));
			usuarioService.saveUsuario(new Usuario(null, "Alejandro Mendoza", "amendoza", "12345", new ArrayList<>()));
			usuarioService.saveUsuario(new Usuario(null, "Marcelo Ramirez", "mramirez", "12345", new ArrayList<>()));
			usuarioService.saveUsuario(new Usuario(null, "Nicolas Chamorro", "nchamorro", "12345", new ArrayList<>()));

			usuarioService.addRolToUsuario("ogomez", "ROL_USER");
			usuarioService.addRolToUsuario("ogomez", "ROL_MANAGER");
			usuarioService.addRolToUsuario("amendoza", "ROL_MANAGER");
			usuarioService.addRolToUsuario("mramirez", "ROL_ADMIN");
			usuarioService.addRolToUsuario("nchamorro", "ROL_SUPER_ADMIN");
			usuarioService.addRolToUsuario("nchamorro", "ROL_ADMIN");
			usuarioService.addRolToUsuario("nchamorro", "ROL_USER");
		};
	}*/

}
