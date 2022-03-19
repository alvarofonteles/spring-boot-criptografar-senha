package expertostech.encriptar.senha.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import expertostech.encriptar.senha.model.UsuarioModel;
import expertostech.encriptar.senha.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PasswordEncoder encoder;

	/**
	 * // Para facilitar o uso do Test usar esse codigo abixo, substituindo o
	 * // @Autowired
	 * 
	 * private final UsuarioRepository repository; private final PasswordEncoder
	 * encoder;
	 * 
	 * public UsuarioController(UsuarioRepository repository, PasswordEncoder
	 * encoder) { this.repository = repository; this.encoder = encoder; }
	 */

	@GetMapping("/listarTodos")
	public ResponseEntity<List<UsuarioModel>> listarTodos() {
		return ResponseEntity.ok(usuarioRepository.findAll());
	}

	@PostMapping("/salvar")
	public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuarioModel) {
		usuarioModel.setPassword(encoder.encode(usuarioModel.getPassword()));
		return ResponseEntity.ok(usuarioRepository.save(usuarioModel));
	}

	@GetMapping("/validarSenha")
	public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password) {

		Optional<UsuarioModel> optUsuario = usuarioRepository.findByLogin(login);
		if (optUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}

		UsuarioModel usuario = optUsuario.get();
		boolean valid = encoder.matches(password, usuario.getPassword());

		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(valid);

	}
}
