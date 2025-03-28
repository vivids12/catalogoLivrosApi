package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroMapper;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.model.Livro;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import com.catalogo.api.catalogoLivros.validacoes.livro.ValidacoesCadastroLivro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private EditoraRepository editoraRepository;

    @Mock
    private List<ValidacoesCadastroLivro> validacoes;

    @MockBean
    private LivroMapper livroMapper;

    @InjectMocks
    private LivroService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Deveria cadastrar livro com sucesso")
    void cadastrarLivroComSucesso() {
        CadastroLivroDto dto = Mockito.mock(CadastroLivroDto.class);
        Editora editora = Mockito.mock(Editora.class);
        Autor autor = Mockito.mock(Autor.class);
        Livro livro = new Livro(dto, autor, editora);

        when(dto.titulo()).thenReturn("");
        when(editoraRepository.getReferenceById(dto.idEditora())).thenReturn(editora);
        when(autorRepository.getReferenceById(dto.idAutor())).thenReturn(autor);
        when(repository.save(Mockito.any())).thenReturn(livro);

        Livro result = service.cadastrar(dto);

        assertEquals(livro, result);
        verify(repository, times(1)).save(Mockito.any(Livro.class));
    }

    @Test
    @DisplayName("Deveria listar todos os livros com sucesso")
    void listarLivrosComSucesso() {
        Autor autor = Mockito.mock(Autor.class);
        Editora editora = Mockito.mock(Editora.class);
        Livro livro = Mockito.mock(Livro.class);

        List<Livro> livros = List.of(livro);
        when(repository.findByStatusTrue()).thenReturn(livros);
        when(livro.getAutor()).thenReturn(autor);
        when(livro.getEditora()).thenReturn(editora);

        List<LivroDto> result = service.listarLivros();

        assertEquals(1, result.size());
        verify(repository, times(1)).findByStatusTrue();
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia quando não existem livros cadastrados")
    void listarLivrosVazio() {
        when(repository.findByStatusTrue()).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarLivros();

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByStatusTrue();
    }

    @Test
    @DisplayName("Deveria retornar um livro")
    void listarPorIdComSucesso() {
        Autor autor = Mockito.mock(Autor.class);
        Editora editora = Mockito.mock(Editora.class);
        Livro livro = Mockito.mock(Livro.class);

        List<Livro> livros = List.of(livro);

        when(repository.findByIdAndStatusTrue(livro.getId())).thenReturn(livros);
        when(livro.getAutor()).thenReturn(autor);
        when(livro.getEditora()).thenReturn(editora);

        LivroDto result = service.listarPorId(livro.getId());

        assertNotNull(result);
        verify(repository, times(1)).findByIdAndStatusTrue(livro.getId());
    }

    @Test
    @DisplayName("Deveria dar erro quando id inválido")
    void listarPorIdInvalido() {
        when(repository.getReferenceByIdAndStatusTrue(anyLong())).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> service.listarPorId(1L));
        verify(repository, times(1)).findByIdAndStatusTrue(anyLong());
    }

    @Test
    @DisplayName("Deveria listar os livros com mesmo titulo")
    void listarPorTituloComSucesso() {
        Livro livro = Mockito.mock(Livro.class);
        Editora editora = Mockito.mock(Editora.class);
        Autor autor = Mockito.mock(Autor.class);

        List<Livro> livros = List.of(livro);
        when(livro.getAutor()).thenReturn(autor);
        when(livro.getEditora()).thenReturn(editora);
        when(repository.findByTituloAndStatusTrue(anyString())).thenReturn(livros);

        List<LivroDto> result = service.listarPorTitulo("titulo");

        assertEquals(1, result.size());
        verify(repository, times(1)).findByTituloAndStatusTrue(anyString());
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia quando titulo invalido")
    void listarPorTituloVazio() {
        when(repository.findByTituloAndStatusTrue(anyString())).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarPorTitulo("titulo");

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByTituloAndStatusTrue(anyString());
    }

    @Test
    @DisplayName("Deveria listar livros do mesmo autor")
    void listarPorAutorComSucesso() {
        Livro livro = Mockito.mock(Livro.class);
        Editora editora = Mockito.mock(Editora.class);
        Autor autor = Mockito.mock(Autor.class);
        Long id = livro.getId();

        List<Livro> livros = List.of(livro);
        List<Long> ids = List.of(id);

        when(livro.getAutor()).thenReturn(autor);
        when(livro.getEditora()).thenReturn(editora);
        when(autorRepository.getIdsByNome(autor.getNome())).thenReturn(ids);
        when(repository.findByAutorIdAndStatusTrue(autor.getId())).thenReturn(livros);

        List<LivroDto> result = service.listarPorAutor(autor.getNome());

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia quando autor não tem livros")
    void listarPorAutorVazio() {
        Autor autor = Mockito.mock(Autor.class);
        List<Long> ids = List.of(autor.getId());
        when(autorRepository.getIdsByNome(autor.getNome())).thenReturn(ids);
        when(repository.findByAutorIdAndStatusTrue(autor.getId())).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarPorAutor(autor.getNome());

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByAutorIdAndStatusTrue(autor.getId());
    }

    @Test
    @DisplayName("Deveria dar erro quando nome do autor invalido")
    void listarPorAutorInvalido() {
        Autor autor = Mockito.mock(Autor.class);
        when(autorRepository.getIdsByNome(autor.getNome())).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarPorAutor(autor.getNome());

        assertNull(result);
        verify(autorRepository, times(1)).getIdsByNome(autor.getNome());
    }

    @Test
    @DisplayName("Deveria retornar uma lista com os livros da mesma editora")
    void listarPorEditoraComSucesso() {
        Livro livro = Mockito.mock(Livro.class);
        Editora editora = Mockito.mock(Editora.class);
        Autor autor = Mockito.mock(Autor.class);
        Long id = livro.getId();

        List<Livro> livros = List.of(livro);
        List<Long> ids = List.of(id);

        when(livro.getAutor()).thenReturn(autor);
        when(livro.getEditora()).thenReturn(editora);
        when(editoraRepository.getIdsByNome(editora.getNome())).thenReturn(ids);
        when(repository.findByEditoraIdAndStatusTrue(editora.getId())).thenReturn(livros);

        List<LivroDto> result = service.listarPorEditora(autor.getNome());

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia quando editora não tiver livros cadastrados")
    void listarPorEditoraVazio() {
        Editora editora = Mockito.mock(Editora.class);
        List<Long> ids = List.of(editora.getId());
        when(editoraRepository.getIdsByNome(editora.getNome())).thenReturn(ids);
        when(repository.findByEditoraIdAndStatusTrue(editora.getId())).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarPorEditora(editora.getNome());

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByEditoraIdAndStatusTrue(editora.getId());
    }

    @Test
    @DisplayName("Deveria dar erro quando id invalido")
    void listarPorEditoraInvalido() {
        Editora editora = Mockito.mock(Editora.class);
        when(editoraRepository.getIdsByNome(editora.getNome())).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarPorEditora(editora.getNome());

        assertNull(result);
        verify(editoraRepository, times(1)).getIdsByNome(editora.getNome());
    }

    @Test
    @DisplayName("Deveria retornar uma lista de livros do mesmo genero")
    void listarPorGeneroComSucesso() {
        Livro livro = Mockito.mock(Livro.class);
        Editora editora = Mockito.mock(Editora.class);
        Autor autor = Mockito.mock(Autor.class);
        Genero genero = Mockito.mock(Genero.class);

        List<Livro> livros = List.of(livro);

        when(livro.getAutor()).thenReturn(autor);
        when(livro.getEditora()).thenReturn(editora);
        when(repository.findByGeneroAndStatusTrue(genero)).thenReturn(livros);

        List<LivroDto> result = service.listarPorGenero(genero);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deveria dar erro quando genero inválido")
    void listarPorGeneroInvalido() {
        Genero genero = Mockito.mock(Genero.class);
        when(repository.findByGeneroAndStatusTrue(genero)).thenReturn(Collections.emptyList());

        List<LivroDto> result = service.listarPorGenero(genero);

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByGeneroAndStatusTrue(genero);
    }
}