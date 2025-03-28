package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroMapper;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.model.Livro;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import com.catalogo.api.catalogoLivros.validacoes.livro.ValidacoesCadastroLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    private LivroMapper livroMapper;

    public Livro cadastrar(CadastroLivroDto dto){
        Autor autor = autorRepository.getReferenceById(dto.idAutor());
        Editora editora = editoraRepository.getReferenceById(dto.idEditora());

        if(autor == null || editora == null){
            throw new ValidacaoException("É necessário ter autor e editora cadastrados.");
        }

        validacoes.forEach(validacao -> validacao.validar(dto));

        return repository.save(new Livro(dto, autor, editora));
    }

    public List<LivroDto> listarPorGenero(Genero genero) {
        List<LivroDto> livros = repository
                .findByGeneroAndStatusTrue(genero)
                .stream()
                .map(LivroDto::new)
                .toList();
        return livros;
    }

    public List<LivroDto> listarPorAutor(String autor) {

        List<Long> idAutor = autorRepository.getIdsByNome(autor);

        if(idAutor.isEmpty()){
            return null;
        }

        List<LivroDto> livros = new ArrayList<>();

        for (Long id : idAutor) {
            List<LivroDto> livrosPorAutor = repository.findByAutorIdAndStatusTrue(id)
                    .stream()
                    .map(LivroDto::new)
                    .toList();

            livros.addAll(livrosPorAutor);
        }


        return livros;
    }

    public List<LivroDto> listarPorEditora(String editora) {
        List<Long> idEditora = editoraRepository.getIdsByNome(editora);
        if(idEditora.isEmpty()){
            return null;
        }
        List<LivroDto> livros = new ArrayList<>();
        for (Long id : idEditora) {
            List<LivroDto> livrosPorEditora = repository.findByEditoraIdAndStatusTrue(id)
                    .stream()
                    .map(LivroDto::new)
                    .toList();
            livros.addAll(livrosPorEditora);
        }
        return livros;
    }

    public List<LivroDto> listarPorTitulo(String titulo) {
        List<LivroDto> livros = repository.findByTituloAndStatusTrue(titulo)
                .stream()
                .map(LivroDto::new)
                .toList();

        return livros;
    }

    public LivroDto listarPorId(Long id) {
        try {
            List<LivroDto> livro = repository.findByIdAndStatusTrue(id)
                    .stream()
                    .map(LivroDto::new)
                    .toList();
            return livro.getFirst();
                    // livroMapper.toDto(livro);
        } catch (ValidacaoException e){
            throw new ValidacaoException("Id inválido!");
        }
    }

    public List<LivroDto> listarLivros() {
        List<LivroDto> livros = repository.findByStatusTrue()
                .stream()
                .map(LivroDto::new)
                .toList();

        return livros;
    }
}
