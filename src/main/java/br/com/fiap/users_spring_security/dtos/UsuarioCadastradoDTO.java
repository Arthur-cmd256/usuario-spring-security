package br.com.fiap.users_spring_security.dtos;

import br.com.fiap.users_spring_security.entities.Perfil;
import br.com.fiap.users_spring_security.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

public record UsuarioCadastradoDTO(
        Long id,
        String nome,
        String email,
        List<String> perfis
) {
    public UsuarioCadastradoDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }
}
