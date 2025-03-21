package com.catalogo.api.catalogoLivros.dto.livro;

import jakarta.validation.constraints.NotNull;

public record AtualizarLivroDto (
        @NotNull
        Long id,
        String titulo,
        Long idEditora
) {
}
