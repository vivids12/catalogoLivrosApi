package com.catalogo.api.catalogoLivros.validacoes.autor;

import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoCpfCadastrado implements ValidacoesCadastroAutor {
    @Autowired
    private AutorRepository repository;

    public void validar(CadastroAutorDto dto){
        Boolean cpfJaCadastrado = repository.existsByCpf(dto.cpf());
        if(cpfJaCadastrado){
            throw new ValidacaoException("CPF já cadastrado.");
        }
    }

}
