package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;

public interface ValidacoesCadastroLivro {
    void validar(CadastroLivroDto dto);
}
