package br.com.fiap.users_spring_security.dtos;

import br.com.fiap.users_spring_security.entities.enums.PerfilNome;
import jakarta.validation.constraints.NotNull;

public record DadosPerfilDTO(
        @NotNull
        PerfilNome perfilNome
) {
}
