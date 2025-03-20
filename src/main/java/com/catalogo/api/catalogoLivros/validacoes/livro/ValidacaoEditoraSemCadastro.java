package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEditoraSemCadastro implements ValidacoesCadastroLivro{
    @Autowired
    private EditoraRepository editoraRepository;

    public void validar(CadastroLivroDto dto) {
        Boolean editoraNaoCsdastrada = editoraRepository.existsById(dto.idEditora());
        if(!editoraNaoCsdastrada){
            throw new ValidacaoException("Editora sem cadastro.");
        }
    }
}
