package com.catalogo.api.catalogoLivros.validacoes.editora;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoCnpjCadastrado implements ValidacoesCadastroEditora {
    @Autowired
    private EditoraRepository repository;

    public void validar(CadastroEditoraDto dto) {
        Boolean jaCadastrado = repository.existsByCnpj(dto.cnpj());
        if (jaCadastrado) {
            throw new ValidacaoException("CNPJ ja cadastrado.");
        }
    }
}
