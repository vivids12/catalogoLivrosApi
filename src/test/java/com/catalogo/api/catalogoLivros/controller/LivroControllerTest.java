package com.catalogo.api.catalogoLivros.controller;

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
import com.catalogo.api.catalogoLivros.service.EditoraService;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
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

        assertEquals(200, response.getStatusCodeValue());
        verify(service, times(1)).cadastrar(any(CadastroLivroDto.class));
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com erros")
    void cadastrarLivroComErro() {
        CadastroLivroDto dto = Mockito.mock(CadastroLivroDto.class);
        when(service.cadastrar(any(CadastroLivroDto.class))).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<String> response = controller.cadastrar(dto);

        assertEquals(400, response.getStatusCodeValue());
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

        assertEquals(400, response.getStatusCodeValue());
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

        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    @DisplayName("Deveria listar por id, quando id existente correto")
    void deveriaDevolverCodigo200IdCorreto(){
        Long id = 1L;
        when(service.listarPorId(id))
                .thenReturn(Mockito.mock(LivroDto.class));

        ResponseEntity<?> response = controller.listarPorId(id);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Não deveria listar por id, quando id inexistente")
    void deveriaDevolverCodigo400IdInexistente(){
        Long id = 1L;
        when(service.listarPorId(id))
                .thenThrow(new ValidacaoException("Id inválido!"));

        ResponseEntity<?> response = controller.listarPorId(id);

        assertEquals(404, response.getStatusCodeValue());
    }

//    @Test
//    @DisplayName("Deveria listar todos os livros")
//    void deveriaDevolverCodigo200ListarLivros() throws Exception {
//        List<LivroDto> lista = new ArrayList<>();
//        lista.add(new LivroDto (1L, "titulo", Genero.FICCAO, "1", "1"));
//        lista.add(new LivroDto (1L, "titulo", Genero.FICCAO, "1", "1"));
//        when(service.listarLivros())
//                .thenReturn(lista);
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/livro")
//        ).andReturn().getResponse();
//
//        assertEquals(200, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Deveria retornar vazio quando não tem livros cadastradas")
//    void deveriaDevolverCodigo404ListarLivros() throws Exception {
//        List<LivroDto> lista = new ArrayList<>();
//        when(service.listarLivros())
//                .thenReturn(lista);
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/livro")
//        ).andReturn().getResponse();
//
//        assertEquals(404, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Deveria listar por nome, quando nome correto")
//    void deveriaDevolverCodigo200NomeCorreto() throws Exception {
//        List<LivroDto> lista = new ArrayList<>();
//        lista.add(new LivroDto (1L, "titulo", Genero.FICCAO, "1", "1"));
//        lista.add(new LivroDto (1L, "titulo", Genero.FICCAO, "1", "1"));
//
//        String titulo = "titulo";
//        when(service.listarPorTitulo(titulo))
//                .thenReturn(lista);
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/livro/titulo/{titulo}", titulo)
//        ).andReturn().getResponse();
//
//        assertEquals(200, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Não deveria listar por titulo, quando titulo incorreto")
//    void deveriaDevolverCodigo400NomeIncorreto() throws Exception {
//        String titulo = "titulo";
//        List<LivroDto> lista = new ArrayList<>();
//        when(service.listarPorTitulo(titulo))
//                .thenReturn(lista);
//
//        MockHttpServletResponse response = mvc.perform(
//                get("/livro/titulo/{titulo}", titulo)
//        ).andReturn().getResponse();
//
//        assertEquals(404, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Deveria atualizar quando informações corretas")
//    void deveriaDevolverCodigo200AtualizarInformacoes() throws Exception {
//        Long id = 1L;
//        Livro livro = new Livro();
//
//        String json = """
//                {
//                    "id": 1,
//                    "idEditora": 1
//                }
//                """;
//
//        when(repository.getReferenceById(id)).thenReturn(livro);
//
//        MockHttpServletResponse response = mvc.perform(
//                put("/livro")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//        ).andReturn().getResponse();
//
//        assertEquals(200, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Não deveria atualizar as informações quando id incorreto")
//    void deveriaDevolverCodigo400NaoAtualizarInformacoes() throws Exception {
//        Long id = 0L;
//
//        String json = """
//                {
//                    "id": 0,
//                    "idEditora": 1
//                }
//                """;
//
//        when(repository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Livro não encontrada!"));
//
//        MockHttpServletResponse response = mvc.perform(
//                put("/livro")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//        ).andReturn().getResponse();
//
//        assertEquals(400, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Deveria excluir livro")
//    void deveriaDevolverCodigo204ExcluirAutor() throws Exception {
//        Long id = 1L;
//        Livro livro = new Livro();
//        livro.setId(id);
//
//        when(repository.getById(id)).thenReturn(livro);
//
//        MockHttpServletResponse response = mvc.perform(
//                delete("/livro/{id}", id)
//        ).andReturn().getResponse();
//
//        assertEquals(204, response.getStatus());
//    }
//
//    @Test
//    @DisplayName("Deveria dar erro ao excluir livro com id incorreto")
//    void deveriaDevolverCodigo400ExcluirAutor() throws Exception {
//        Long id = 0L;
//
//        when(repository.getById(id)).thenThrow(new EntityNotFoundException("Id incorreto!"));
//
//        MockHttpServletResponse response = mvc.perform(
//                delete("/livro/{id}", id)
//        ).andReturn().getResponse();
//
//        assertEquals(400, response.getStatus());
//    }

}