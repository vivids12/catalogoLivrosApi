package com.catalogo.api.catalogoLivros.dto.editora;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarEditoraDto(
        @NotNull
        Long id,
        @NotBlank
        String nome,
        @Email
        String email
) {
}
