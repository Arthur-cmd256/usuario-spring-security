package br.com.fiap.users_spring_security.entities;

import br.com.fiap.users_spring_security.entities.enums.PerfilNome;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "tb_perfil")
public class Perfil implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PerfilNome nome;
    @ManyToMany(mappedBy = "perfis", fetch = FetchType.EAGER)
    private Set<Usuario> usuarios;

    @Override
    public @Nullable String getAuthority() {
        return "ROLE_" + this.nome;
    }
}
