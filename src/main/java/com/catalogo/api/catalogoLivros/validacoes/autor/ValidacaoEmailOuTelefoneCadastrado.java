package com.catalogo.api.catalogoLivros.validacoes.autor;

import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmailOuTelefoneCadastrado implements ValidacoesCadastroAutor {
    @Autowired
    private AutorRepository repository;

    public void validar(CadastroAutorDto dto){
        Boolean emailETelefoneJaCadastrado = repository.existsByEmailOrTelefone(dto.email(), dto.telefone());
        if(emailETelefoneJaCadastrado){
            throw new ValidacaoException("Email ou telefone já cadastrado.");
        }
    }

}
