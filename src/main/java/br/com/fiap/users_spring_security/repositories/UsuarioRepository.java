package br.com.fiap.users_spring_security.repositories;

import br.com.fiap.users_spring_security.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<UserDetails> findByEmailIgnoreCase(String username);
}
