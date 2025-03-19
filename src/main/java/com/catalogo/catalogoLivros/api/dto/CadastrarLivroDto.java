package com.catalogo.catalogoLivros.api.dto;

import com.catalogo.catalogoLivros.api.model.Autor;
import com.catalogo.catalogoLivros.api.model.Editora;
import com.catalogo.catalogoLivros.api.model.Genero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;



public record CadastrarLivroDto(
        @NotBlank
        String titulo,
        Genero genero,
        Autor autor,
        Editora editora) {
}
