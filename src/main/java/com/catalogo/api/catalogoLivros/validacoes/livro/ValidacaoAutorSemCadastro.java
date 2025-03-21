package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ValidacaoAutorSemCadastro implements ValidacoesCadastroLivro{
    @Autowired
    private AutorRepository autorRepository;

    public void validar(CadastroLivroDto dto) {
        Boolean autorNaoCadastrado = autorRepository.existsById(dto.idAutor());
        if(!autorNaoCadastrado){
            throw new ValidacaoException("Autor sem cadastro.");
        }
    }
}
