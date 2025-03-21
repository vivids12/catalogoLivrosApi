package com.catalogo.api.catalogoLivros.dto;

import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.model.Livro;

public record LivroDto (
        String titulo,
        Genero genero,
        String nomeAutor,
        String nomeEditora){
    public LivroDto(Livro livro) {
        this(
            livro.getTitulo(),
            livro.getGenero(),
            livro.getAutor().getNome(),
            livro.getEditora().getNome()
        );
    }
}
