package com.catalogo.api.catalogoLivros.service;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.catalogo.api.catalogoLivros.dto.autor.AutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.validacoes.autor.ValidacoesCadastroAutor;
import com.fasterxml.jackson.annotation.JacksonInject;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AutorServiceTest {

    @Mock
    private AutorRepository repository;

    @Mock
    private List<ValidacoesCadastroAutor> validacoes;

    @InjectMocks
    private AutorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void cadastrarAutorComSucesso() {
        CadastroAutorDto dto = Mockito.mock(CadastroAutorDto.class);
        when(dto.nome()).thenReturn("");
        when(dto.email()).thenReturn("");
        Autor autor = new Autor(dto);
        when(repository.save(Mockito.any())).thenReturn(autor);

        Autor result = service.cadastrar(dto);

        assertEquals(autor, result);
        verify(repository, times(1)).save(Mockito.any(Autor.class));
    }

    @Test
    void listarAutoresComSucesso() {
        List<Autor> autores = List.of(new Autor());
        when(repository.findByStatusTrue()).thenReturn(autores);

        List<AutorDto> result = service.listarAutores();

        assertEquals(1, result.size());
        verify(repository, times(1)).findByStatusTrue();
    }

    @Test
    void listarAutoresVazio() {
        when(repository.findByStatusTrue()).thenReturn(Collections.emptyList());

        List<AutorDto> result = service.listarAutores();

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByStatusTrue();
    }

    @Test
    void listarPorIdComSucesso() {
        Autor autor = new Autor();
        when(repository.getReferenceByIdAndStatusTrue(anyLong())).thenReturn(new AutorDto(autor));

        AutorDto result = service.listarPorId(1L);

        assertNotNull(result);
        verify(repository, times(1)).getReferenceByIdAndStatusTrue(anyLong());
    }

    @Test
    void listarPorIdInvalido() {
        when(repository.getReferenceByIdAndStatusTrue(anyLong())).thenThrow(new ValidacaoException("Id inválido!"));

        assertThrows(ValidacaoException.class, () -> service.listarPorId(1L));
        verify(repository, times(1)).getReferenceByIdAndStatusTrue(anyLong());
    }

    @Test
    void listarPorNomeComSucesso() {
        List<Autor> autores = List.of(new Autor());
        when(repository.findByNomeAndStatusTrue(anyString())).thenReturn(autores);

        List<AutorDto> result = service.listarPorNome("Nome");

        assertEquals(1, result.size());
        verify(repository, times(1)).findByNomeAndStatusTrue(anyString());
    }

    @Test
    void listarPorNomeVazio() {
        when(repository.findByNomeAndStatusTrue(anyString())).thenReturn(Collections.emptyList());

        List<AutorDto> result = service.listarPorNome("Nome");

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByNomeAndStatusTrue(anyString());
    }

}