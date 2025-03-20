package com.catalogo.api.catalogoLivros.dto;

import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.model.Genero;
import jakarta.validation.constraints.NotBlank;

public record CadastroLivroDto(
        @NotBlank
        String titulo,
        Genero genero,
        Long idAutor,
        Long idEditora) {
}
