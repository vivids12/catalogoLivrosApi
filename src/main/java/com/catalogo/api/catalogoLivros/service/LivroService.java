package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.model.Livro;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import com.catalogo.api.catalogoLivros.validacoes.livro.ValidacoesCadastroLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private List<ValidacoesCadastroLivro> validacoes;

    public void cadastrar(CadastroLivroDto dto){
        Autor autor = autorRepository.getReferenceById(dto.idAutor());
        Editora editora = editoraRepository.getReferenceById(dto.idEditora());

        validacoes.forEach(validacao -> validacao.validar(dto));

        repository.save(new Livro(dto, autor, editora));
    }

}
