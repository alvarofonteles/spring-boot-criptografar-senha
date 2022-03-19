package expertostech.encriptar.senha.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import expertostech.encriptar.senha.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

	// evitar NullPointerException
	public Optional<UsuarioModel> findByLogin(String login);

}
