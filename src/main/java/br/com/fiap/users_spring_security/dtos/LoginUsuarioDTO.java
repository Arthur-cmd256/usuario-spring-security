package br.com.fiap.users_spring_security.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioDTO(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
