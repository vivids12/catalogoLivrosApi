package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.autor.AtualizarAutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.AutorDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroMapper;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.service.AutorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AutorService service;

    @MockBean
    private LivroMapper livroMapper;

    @Mock
    private AutorRepository repository;

    @Test
    @DisplayName("Não deveria permitir cadastro com erros")
    void deveriaDeveolverCodigo400() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/autor")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria permitir cadastro completo sem erros")
    void deveriaDeveolverCodigo200() throws Exception {
        String json = """
                {
                "nome": "Silvia Santos",
                "email": "silvia.santos@gmail.com",
                "cpf": "12300012300",
                "telefone": "(11)9000-0000"
                }
                """;

        var response = mvc.perform(
                post("/autor")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria listar por id, quando id existente correto")
    void deveriaDevolverCodigo200IdCorreto() throws Exception {
        Long id = 1L;
        when(service.listarPorId(id))
                .thenReturn(new AutorDto(1l, "Nome", "email", "11", "11"));

        MockHttpServletResponse response = mvc.perform(
                get("/autor/{id}", id)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Não deveria listar por id, quando id inexistente")
    void deveriaDevolverCodigo400IdInexistente() throws Exception {
        Long id = 1L;
        when(service.listarPorId(id))
                .thenThrow(new ValidacaoException("Id inválido!"));
        MockHttpServletResponse response = mvc.perform(
                get("/autor/{id}", id)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria listar todos os autores")
    void deveriaDevolverCodigo200ListarAutores() throws Exception {
        List<AutorDto> lista = new ArrayList<>();
        lista.add(new AutorDto (1l, "Nome", "email", "11", "11"));
        lista.add(new AutorDto (2l, "Nome", "email", "11", "11"));
        when(service.listarAutores())
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/autor")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar vazio quando não tem autores cadastrados")
    void deveriaDevolverCodigo404ListarAutores() throws Exception {
        List<AutorDto> lista = new ArrayList<>();
        when(service.listarAutores())
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/autor")
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deveria listar por nome, quando nome correto")
    void deveriaDevolverCodigo200NomeCorreto() throws Exception {
        List<AutorDto> lista = new ArrayList<>();
        lista.add(new AutorDto (1l, "Nome", "email", "11", "11"));
        lista.add(new AutorDto (2l, "Nome", "email", "11", "11"));

        String nome = "nome";
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/autor/nome/{nome}", nome)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Não deveria listar por nome, quando nome incorreto")
    void deveriaDevolverCodigo400NomeIncorreto() throws Exception {
        String nome = "nome";
        List<AutorDto> lista = new ArrayList<>();
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/autor/nome/{nome}", nome)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deveria atualizar quando informações corretas")
    void deveriaDevolverCodigo200AtualizarInformacoes() throws Exception {
        Long id = 1L;
        Autor autor = new Autor();

        String json = """
                {
                    "id": 1,
                    "nome": "Silvia Santos",
                    "email": "silvia.santos@gmail.com"
                }
                """;

        when(repository.getReferenceById(id)).thenReturn(autor);

        MockHttpServletResponse response = mvc.perform(
                put("/autor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Não deveria atualizar as informações quando id incorreto")
    void deveriaDevolverCodigo400NaoAtualizarInformacoes() throws Exception {
        Long id = 0L;

        String json = """
                {
                    "id": 0
                    "nome": "Silvia Santos",
                    "email": "silvia.santos@gmail.com"
                }
                """;

        when(repository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Autor não encontrado!"));

        MockHttpServletResponse response = mvc.perform(
                put("/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria excluir autor")
    void deveriaDevolverCodigo204ExcluirAutor() throws Exception {
        Long id = 1L;
        Autor autor = new Autor();
        autor.setId(id);

        when(repository.getById(id)).thenReturn(autor);

        MockHttpServletResponse response = mvc.perform(
                delete("/autor/{id}", id)
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Deveria dar erro ao excluir autor com id incorreto")
    void deveriaDevolverCodigo400ExcluirAutor() throws Exception {
        Long id = 0L;

        when(repository.getById(id)).thenThrow(new EntityNotFoundException("Id incorreto!"));

        MockHttpServletResponse response = mvc.perform(
                delete("/autor/{id}", id)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

}