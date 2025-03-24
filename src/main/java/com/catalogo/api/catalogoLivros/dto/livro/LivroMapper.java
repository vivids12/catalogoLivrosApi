package com.catalogo.api.catalogoLivros.dto.livro;

import com.catalogo.api.catalogoLivros.model.Livro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    LivroDto toDto(Livro livro);
    Livro toEntity(LivroDto dto);
}