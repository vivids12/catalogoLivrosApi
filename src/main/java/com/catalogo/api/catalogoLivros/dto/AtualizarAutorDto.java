package com.catalogo.api.catalogoLivros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AtualizarAutorDto(
        @NotNull
        Long id,
        String nome,
        @Email
        String email,
        String telefone
) {
}
