package com.catalogo.api.catalogoLivros.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroEditoraDto(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String cnpj
) {
}
