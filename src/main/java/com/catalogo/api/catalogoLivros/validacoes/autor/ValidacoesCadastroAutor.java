package com.catalogo.api.catalogoLivros.validacoes.autor;

import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;

public interface ValidacoesCadastroAutor {
    void validar(CadastroAutorDto dto);
}
