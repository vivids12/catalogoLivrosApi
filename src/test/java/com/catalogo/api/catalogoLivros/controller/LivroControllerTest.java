package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.livro.AtualizarLivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroMapper;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.model.Livro;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import com.catalogo.api.catalogoLivros.service.LivroService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class LivroControllerTest {
    @InjectMocks
    private LivroController controller;
    @Autowired
    private MockMvc mvc;
    @Mock
    private LivroRepository repository;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private EditoraRepository editoraRepository;
    @Mock
    private LivroService service;

    @MockBean
    private LivroMapper livroMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deveria permitir cadastro sem erros")
    void cadastrarLivroComSucesso() {
        CadastroLivroDto dto = Mockito.mock(CadastroLivroDto.class);
        Livro livro = new Livro();
        when(service.cadastrar(any(CadastroLivroDto.class))).thenReturn(livro);

        ResponseEntity<String> response = controller.cadastrar(dto);

        assertEquals(200, response.getStatusCode().value());
        verify(service, times(1)).cadastrar(any(CadastroLivroDto.class));
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com erros")
    void cadastrarLivroComErro() {
        CadastroLivroDto dto = Mockito.mock(CadastroLivroDto.class);
        when(service.cadastrar(any(CadastroLivroDto.class))).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<String> response = controller.cadastrar(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria permitir cadastro incompleto")
    void deveriaDevolverCodigo400() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/livro")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com id do autor incorreto")
    void deveriaDevolverCodigo400AutorIncorreto(){
        Editora editora = new Editora();
        Long id = 1L;
        CadastroLivroDto dto = Mockito.mock(CadastroLivroDto.class);

        when(autorRepository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Autor incorreto"));
        when(editoraRepository.getReferenceById(id)).thenReturn(editora);

        ResponseEntity<?> response = controller.cadastrar(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com id da editora incorreto")
    void deveriaDevolverCodigo400EditoraIncorreto() {
        Autor autor = new Autor();
        Long id = 1L;
        CadastroLivroDto dto = Mockito.mock(CadastroLivroDto.class);

        when(autorRepository.getReferenceById(id)).thenReturn(autor);
        when(editoraRepository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Editora incorreta"));

        ResponseEntity<?> response = controller.cadastrar(dto);

        assertEquals(400, response.getStatusCode().value());
    }


    @Test
    @DisplayName("Deveria listar por id, quando id existente correto")
    void deveriaDevolverCodigo200IdCorreto(){
        Long id = 1L;
        when(service.listarPorId(id))
                .thenReturn(Mockito.mock(LivroDto.class));

        ResponseEntity<?> response = controller.listarPorId(id);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria listar por id, quando id inexistente")
    void deveriaDevolverCodigo400IdInexistente(){
        Long id = 1L;
        when(service.listarPorId(id))
                .thenThrow(new ValidacaoException("Id inválido!"));

        ResponseEntity<?> response = controller.listarPorId(id);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar todos os livros")
    void deveriaDevolverCodigo200ListarLivros(){
        List<LivroDto> lista = new ArrayList<>();
        lista.add(Mockito.mock(LivroDto.class));
        lista.add(Mockito.mock(LivroDto.class));

        when(service.listarLivros()).thenReturn(lista);

        ResponseEntity<?> response = controller.listarLivros();

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria retornar vazio quando não tem livros cadastradas")
    void deveriaDevolverCodigo404ListarLivros(){
        List<LivroDto> lista = new ArrayList<>();
        when(service.listarLivros())
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarLivros();

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar por nome, quando nome correto")
    void deveriaDevolverCodigo200NomeCorreto(){
        List<LivroDto> lista = new ArrayList<>();
        lista.add(Mockito.mock(LivroDto.class));
        lista.add(Mockito.mock(LivroDto.class));

        String titulo = "titulo";
        when(service.listarPorTitulo(titulo))
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarPorNome(titulo);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria listar por titulo, quando titulo incorreto")
    void deveriaDevolverCodigo400NomeIncorreto(){
        String titulo = "titulo";
        List<LivroDto> lista = new ArrayList<>();
        when(service.listarPorTitulo(titulo))
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarPorNome(titulo);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria atualizar quando informações corretas")
    void deveriaDevolverCodigo200AtualizarInformacoes(){
        AtualizarLivroDto atualizarDto = Mockito.mock(AtualizarLivroDto.class);
        Livro livro = Mockito.mock(Livro.class);
        Editora editora = Mockito.mock(Editora.class);

        when(repository.getReferenceById(atualizarDto.id())).thenReturn(livro);
        when(editoraRepository.getReferenceById(atualizarDto.idEditora())).thenReturn(editora);

        ResponseEntity<?> response = controller.atualizar(atualizarDto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria atualizar as informações quando id incorreto")
    void deveriaDevolverCodigo400NaoAtualizarInformacoes(){
        Long id = 1L;
        AtualizarLivroDto atualizarDto = Mockito.mock(AtualizarLivroDto.class);
        Editora editora = Mockito.mock(Editora.class);

        when(repository.getReferenceById(atualizarDto.id())).thenThrow(new EntityNotFoundException("Livro não encontrada!"));
        when(editoraRepository.getReferenceById(atualizarDto.idEditora())).thenReturn(editora);

        ResponseEntity<?> response = controller.atualizar(atualizarDto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria excluir livro")
    void deveriaDevolverCodigo204ExcluirAutor(){
        Livro livro = Mockito.mock(Livro.class);

        when(repository.getById(livro.getId())).thenReturn(livro);

        ResponseEntity<?> response = controller.excluir(livro.getId());

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria dar erro ao excluir livro com id incorreto")
    void deveriaDevolverCodigo400ExcluirAutor(){
        Livro livro = Mockito.mock(Livro.class);

        when(repository.getById(livro.getId())).thenThrow(new EntityNotFoundException("Id incorreto!"));

        ResponseEntity<?> response = controller.excluir(livro.getId());

        assertEquals(400, response.getStatusCode().value());
    }

}