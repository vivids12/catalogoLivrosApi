package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.autor.AutorDto;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.service.AutorService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AutorService service;

    @Mock
    private Autor autor;

    @Mock
    private AutorDto dto;

    @Mock
    private AutorRepository autorRepository;

    @Test
    @DisplayName("Cadastro com erros")
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
    @DisplayName("Cadastro completo sem erros")
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
    @DisplayName("Listar por id correto")
    void deveriaDevolverCodigo200IdCorreto() throws Exception {
        when(service.listarPorId(1L))
                .thenReturn(new AutorDto(1l, "Nome", "email", "11", "11"));
        Long id = 1L;
        MockHttpServletResponse response = mvc.perform(
                get("/autor/{id}", id)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

}