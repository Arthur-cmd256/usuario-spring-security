package br.com.fiap.users_spring_security.repositories;

import br.com.fiap.users_spring_security.entities.Perfil;
import br.com.fiap.users_spring_security.entities.enums.PerfilNome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByNome(PerfilNome nome);
}
