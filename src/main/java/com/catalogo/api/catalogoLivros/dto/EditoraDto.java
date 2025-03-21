package com.catalogo.api.catalogoLivros.dto;

import com.catalogo.api.catalogoLivros.model.Editora;

public record EditoraDto(
        String nome,
        String cnpj
) {
    public EditoraDto(Editora editora) {
        this(editora.getNome(), editora.getCnpj());
    }
}
