package com.catalogo.api.catalogoLivros.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarLivroDto (
        @NotNull
        Long id,
        String titulo,
        Long idEditora
) {
}
