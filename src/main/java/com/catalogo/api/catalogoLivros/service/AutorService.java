package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    @Autowired
    private AutorRepository repository;

    public void cadastrar(CadastroAutorDto dto){
        Boolean cpfJaCadastrado = repository.existsByCpf(dto.cpf());
        Boolean emailETelefoneJaCadastrado = repository.exitsByEmailOrTelefone(dto.email(), dto.telefone());

        if(cpfJaCadastrado){
            throw new ValidacaoException("CPF já cadastrado.");
        }
        if(emailETelefoneJaCadastrado){
            throw new ValidacaoException("Email ou telefone já cadastrado.");
        }

        repository.save(new Autor(dto));

    }
}
