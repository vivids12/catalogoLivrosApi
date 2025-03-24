package com.catalogo.api.catalogoLivros.dto.livro;

import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.model.Livro;

public record LivroDto (
        Long id,
        String titulo,
        Genero genero,
        String idAutor,
        String idEditora){
    public LivroDto(Livro livro) {
        this(
            livro.getId(),
            livro.getTitulo(),
            livro.getGenero(),
            livro.getAutor().getNome(),
            livro.getEditora().getNome()
        );
    }
}
