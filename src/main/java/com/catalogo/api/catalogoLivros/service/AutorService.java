package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.validacoes.autor.ValidacoesCadastroAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository repository;

    @Autowired
    private List<ValidacoesCadastroAutor> validacoes;

    public void cadastrar(CadastroAutorDto dto){
        validacoes.forEach(validacao -> validacao.validar(dto));

        repository.save(new Autor(dto));

    }
}
