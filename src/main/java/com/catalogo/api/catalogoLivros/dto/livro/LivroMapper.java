package com.catalogo.api.catalogoLivros.dto.livro;

import com.catalogo.api.catalogoLivros.model.Livro;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Component
public interface LivroMapper {
    LivroDto toDto(Livro livro);
    Livro toEntity(LivroDto dto);
}