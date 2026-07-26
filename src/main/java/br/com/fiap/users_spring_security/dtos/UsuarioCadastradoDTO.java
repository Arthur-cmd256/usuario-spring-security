package br.com.fiap.users_spring_security.dtos;

import br.com.fiap.users_spring_security.entities.Usuario;

public record UsuarioCadastradoDTO(
        Long id,
        String nome,
        String email
) {
    public UsuarioCadastradoDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
