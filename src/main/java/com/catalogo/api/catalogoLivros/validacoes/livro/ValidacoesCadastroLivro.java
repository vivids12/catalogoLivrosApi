package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;

public interface ValidacoesCadastroLivro {
    void validar(CadastroLivroDto dto);
}
