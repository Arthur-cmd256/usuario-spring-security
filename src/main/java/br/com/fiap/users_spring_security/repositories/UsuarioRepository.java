package br.com.fiap.users_spring_security.repositories;

import br.com.fiap.users_spring_security.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
