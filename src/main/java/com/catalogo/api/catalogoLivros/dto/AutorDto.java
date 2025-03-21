package com.catalogo.api.catalogoLivros.dto;

import com.catalogo.api.catalogoLivros.model.Autor;

public record AutorDto(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf
) {
    public AutorDto(Autor autor) {
        this(
            autor.getId(),
            autor.getNome(),
            autor.getEmail(),
            autor.getTelefone(),
            autor.getCpf()
        );
    }
}
