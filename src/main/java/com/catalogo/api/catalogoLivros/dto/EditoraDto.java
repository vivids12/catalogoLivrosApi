package com.catalogo.api.catalogoLivros.dto;

import com.catalogo.api.catalogoLivros.model.Editora;

public record EditoraDto(
        Long id,
        String nome,
        String cnpj
) {
    public EditoraDto(Editora editora) {
        this(
            editora.getId(),
            editora.getNome(),
            editora.getCnpj()
        );
    }
}
