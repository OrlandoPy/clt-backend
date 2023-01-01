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

			usuarioService.saveUsuario(new Usuario(null, "ogomez@gmail.com", "12345", "Orlando Gomez", "98765432", 0L, new ArrayList<>()));
			usuarioService.saveUsuario(new Usuario(null, "amendoza@gmail.com", "12345", "Alejandro Mendoza", "2345678", 0L, new ArrayList<>()));
			usuarioService.saveUsuario(new Usuario(null, "mramirez@gmail.com", "12345", "Marcelo Ramirez", "5624562", 0L, new ArrayList<>()));
			usuarioService.saveUsuario(new Usuario(null, "nchamorro@gmail.com", "12345", "Nicolas Chamorro", "66775656 ", 0L, new ArrayList<>()));

			usuarioService.addRolToUsuario("ogomez@gmail.com", "ROL_USER");
			usuarioService.addRolToUsuario("ogomez@gmail.com", "ROL_MANAGER");
			usuarioService.addRolToUsuario("amendoza@gmail.com", "ROL_MANAGER");
			usuarioService.addRolToUsuario("mramirez@gmail.com", "ROL_ADMIN");
			usuarioService.addRolToUsuario("nchamorro@gmail.com", "ROL_SUPER_ADMIN");
			usuarioService.addRolToUsuario("nchamorro@gmail.com", "ROL_ADMIN");
			usuarioService.addRolToUsuario("nchamorro@gmail.com", "ROL_USER");
		};
	}*/

}
