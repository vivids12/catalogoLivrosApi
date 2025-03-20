package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.validacoes.editora.ValidacoesCadastroEditora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {
    @Autowired
    private EditoraRepository repository;

    @Autowired
    private List<ValidacoesCadastroEditora> validacoes;

    public void cadastrar(CadastroEditoraDto dto){
        validacoes.forEach(validacao -> validacao.validar(dto));

        repository.save(new Editora(dto));
    }
}
