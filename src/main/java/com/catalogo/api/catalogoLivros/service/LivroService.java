package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.model.Livro;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    public void cadastrar(CadastroLivroDto dto){
        Autor autor = autorRepository.getReferenceById(dto.idAutor());
        Editora editora = editoraRepository.getReferenceById(dto.idEditora());
        Boolean jaCadastradoTituloEAutor = repository.existsByTituloAndAutorId(dto.titulo(), dto.idAutor());
        if(jaCadastradoTituloEAutor){
            throw new ValidacaoException("Livro ja cadastrado.");
        }
        Boolean autorNaoCadastrado = autorRepository.existsById(dto.idAutor());
        if(!autorNaoCadastrado){
            throw new ValidacaoException("Autor sem cadastro.");
        }
        repository.save(new Livro(dto, autor, editora));
    }

}
