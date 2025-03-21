package com.catalogo.api.catalogoLivros.dto.livro;

import com.catalogo.api.catalogoLivros.model.Genero;
import jakarta.validation.constraints.NotBlank;

public record CadastroLivroDto(
        @NotBlank
        String titulo,
        Genero genero,
        Long idAutor,
        Long idEditora) {
}
