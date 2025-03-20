package com.catalogo.api.catalogoLivros.validacoes.editora;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;

public interface ValidacoesCadastroEditora {
    void validar(CadastroEditoraDto dto);
}
