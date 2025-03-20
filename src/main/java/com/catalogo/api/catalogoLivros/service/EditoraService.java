package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditoraService {
    @Autowired
    private EditoraRepository repository;

    public void cadastrar(CadastroEditoraDto dto){
        Boolean jaCadastrado = repository.existsByCnpj(dto.cnpj());
        if(jaCadastrado){
            throw new ValidacaoException("CNPJ ja cadastrado.");
        }
        Boolean emailJaCadastrado = repository.existsByEmail(dto.email());
        if(emailJaCadastrado){
            throw new ValidacaoException("E-mail ja cadastrado.");
        }

        repository.save(new Editora(dto));
    }
}
