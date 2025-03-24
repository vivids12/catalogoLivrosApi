package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.editora.EditoraDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroMapper;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.service.EditoraService;
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
class EditoraControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EditoraService service;

    @MockBean
    private LivroMapper livroMapper;

    @Mock
    private EditoraRepository repository;



    @Test
    @DisplayName("Não deveria permitir cadastro com erros")
    void deveriaDevolverCodigo400() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/editora")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria permitir cadastro completo sem erros")
    void deveriaDevolverCodigo200() throws Exception {
        String json = """
                {
                "nome": "Silvia Santos",
                "email": "silvia.santos@gmail.com",
                "cnpj": "56.461.000/0002-93"
                }
                """;

        var response = mvc.perform(
                post("/editora")
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
                .thenReturn(new EditoraDto(1l, "Nome", "11"));

        MockHttpServletResponse response = mvc.perform(
                get("/editora/{id}", id)
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
                get("/editora/{id}", id)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria listar todos os editoras")
    void deveriaDevolverCodigo200ListarEditoras() throws Exception {
        List<EditoraDto> lista = new ArrayList<>();
        lista.add(new EditoraDto (1l, "Nome", "11"));
        lista.add(new EditoraDto (2l, "Nome", "11"));
        when(service.listarEditoras())
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/editora")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar vazio quando não tem editoras cadastradas")
    void deveriaDevolverCodigo404ListarEditoras() throws Exception {
        List<EditoraDto> lista = new ArrayList<>();
        when(service.listarEditoras())
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/editora")
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deveria listar por nome, quando nome correto")
    void deveriaDevolverCodigo200NomeCorreto() throws Exception {
        List<EditoraDto> lista = new ArrayList<>();
        lista.add(new EditoraDto (1l, "nome", "11"));
        lista.add(new EditoraDto (2l, "nome", "11"));

        String nome = "nome";
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/editora/nome/{nome}", nome)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Não deveria listar por nome, quando nome incorreto")
    void deveriaDevolverCodigo400NomeIncorreto() throws Exception {
        String nome = "nome";
        List<EditoraDto> lista = new ArrayList<>();
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        MockHttpServletResponse response = mvc.perform(
                get("/editora/nome/{nome}", nome)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deveria atualizar quando informações corretas")
    void deveriaDevolverCodigo200AtualizarInformacoes() throws Exception {
        Long id = 1L;
        Editora editora = new Editora();

        String json = """
                {
                    "id": 1,
                    "nome": "Silvia Santos",
                    "email": "silvia.santos@gmail.com"
                }
                """;

        when(repository.getReferenceById(id)).thenReturn(editora);

        MockHttpServletResponse response = mvc.perform(
                put("/editora")
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

        when(repository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Editora não encontrada!"));

        MockHttpServletResponse response = mvc.perform(
                put("/editora")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria excluir editora")
    void deveriaDevolverCodigo204ExcluirAutor() throws Exception {
        Long id = 1L;
        Editora editora = new Editora();
        editora.setId(id);

        when(repository.getById(id)).thenReturn(editora);

        MockHttpServletResponse response = mvc.perform(
                delete("/editora/{id}", id)
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Deveria dar erro ao excluir editora com id incorreto")
    void deveriaDevolverCodigo400ExcluirAutor() throws Exception {
        Long id = 0L;

        when(repository.getById(id)).thenThrow(new EntityNotFoundException("Id incorreto!"));

        MockHttpServletResponse response = mvc.perform(
                delete("/editora/{id}", id)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

}