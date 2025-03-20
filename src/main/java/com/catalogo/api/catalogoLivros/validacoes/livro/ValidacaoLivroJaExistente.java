package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoLivroJaExistente implements ValidacoesCadastroLivro {
    @Autowired
    private LivroRepository repository;

    public void validar(CadastroLivroDto dto) {
        Boolean jaCadastradoTituloEAutor = repository.existsByTituloAndAutorId(dto.titulo(), dto.idAutor());
        if(jaCadastradoTituloEAutor){
            throw new ValidacaoException("Livro já cadastrado.");
        }
    }
}
