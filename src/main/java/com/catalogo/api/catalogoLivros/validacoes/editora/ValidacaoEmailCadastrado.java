package com.catalogo.api.catalogoLivros.validacoes.editora;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmailCadastrado implements ValidacoesCadastroEditora {
    @Autowired
    private EditoraRepository repository;

    public void validar(CadastroEditoraDto dto) {
        Boolean emailJaCadastrado = repository.existsByEmail(dto.email());
        if (emailJaCadastrado) {
            throw new ValidacaoException("E-mail ja cadastrado.");
        }
    }
}
