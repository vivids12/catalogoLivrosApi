package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.editora.EditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.validacoes.editora.ValidacoesCadastroEditora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EditoraServiceTest {

    @Mock
    private EditoraRepository repository;

    @Mock
    private List<ValidacoesCadastroEditora> validacoes;

    @InjectMocks
    private EditoraService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void cadastrarEditoraComSucesso() {
        CadastroEditoraDto dto = Mockito.mock(CadastroEditoraDto.class);
        when(dto.nome()).thenReturn("");
        when(dto.email()).thenReturn("");
        Editora autor = new Editora(dto);
        when(repository.save(Mockito.any())).thenReturn(autor);

        Editora result = service.cadastrar(dto);

        assertEquals(autor, result);
        verify(repository, times(1)).save(Mockito.any(Editora.class));
    }

    @Test
    void listarEditorasComSucesso() {
        List<Editora> autores = List.of(new Editora());
        when(repository.findByStatusTrue()).thenReturn(autores);

        List<EditoraDto> result = service.listarEditoras();

        assertEquals(1, result.size());
        verify(repository, times(1)).findByStatusTrue();
    }

    @Test
    void listarEditorasVazio() {
        when(repository.findByStatusTrue()).thenReturn(Collections.emptyList());

        List<EditoraDto> result = service.listarEditoras();

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByStatusTrue();
    }

    @Test
    void listarPorIdComSucesso() {
        Editora autor = new Editora();
        when(repository.getReferenceByIdAndStatusTrue(anyLong())).thenReturn(new EditoraDto(autor));

        EditoraDto result = service.listarPorId(1L);

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
        List<Editora> autores = List.of(new Editora());
        when(repository.findByNomeAndStatusTrue(anyString())).thenReturn(autores);

        List<EditoraDto> result = service.listarPorNome("Nome");

        assertEquals(1, result.size());
        verify(repository, times(1)).findByNomeAndStatusTrue(anyString());
    }

    @Test
    void listarPorNomeVazio() {
        when(repository.findByNomeAndStatusTrue(anyString())).thenReturn(Collections.emptyList());

        List<EditoraDto> result = service.listarPorNome("Nome");

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByNomeAndStatusTrue(anyString());
    }

}